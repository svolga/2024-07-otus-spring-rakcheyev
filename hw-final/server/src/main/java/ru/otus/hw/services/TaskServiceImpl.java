package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.TaskDto;
import ru.otus.hw.dto.TaskInfoDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.TaskMapper;
import ru.otus.hw.models.Group;
import ru.otus.hw.models.Task;
import ru.otus.hw.models.Teacher;
import ru.otus.hw.repositories.GroupRepository;
import ru.otus.hw.repositories.TaskRepository;
import ru.otus.hw.repositories.TeacherRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;


    @Override
    @Transactional(readOnly = true)
    public TaskDto findById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id %d not found".formatted(id)));
        return taskMapper.toDto(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskInfoDto> findAll() {
        var tasks = taskRepository.findAll();
        return taskMapper.toTaskInfoDtos(tasks);
    }

    @Override
    @Transactional
    public TaskDto create(TaskDto taskDto) {
        taskDto.setId(0L);
        Task task = taskMapper.toEntity(taskDto, getTeacher(taskDto.getTeacherDto().getId()),
                getGroup(taskDto.getGroupDto().getId()));
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskDto update(TaskDto taskDto) {
        validate(taskDto.getId());
        Task task = taskMapper.toEntity(taskDto, getTeacher(taskDto.getTeacherDto().getId()),
                getGroup(taskDto.getGroupDto().getId()));
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        validate(id);
        taskRepository.deleteById(id);
    }

/*    private Task save(long id, String title, long teacherId, long groupId) {

        var teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id %d not found".formatted(teacherId)));

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id %d not found".formatted(groupId)));

        var task = new Task(id, title, teacher, group);
        return taskRepository.save(task);
    }*/

    private void validate(long id) {
        var task = findById(id);
        if (task == null) {
            throw new EntityNotFoundException("Task with id %d not found".formatted(id));
        }
    }

    private Teacher getTeacher(long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id %d not found".formatted(teacherId)));
    }

    private Group getGroup(long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id %d not found".formatted(groupId)));
    }

}
