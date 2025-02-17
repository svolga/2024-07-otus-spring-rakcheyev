package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.HomeworkDto;
import ru.otus.hw.dto.HomeworkInfoDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.HomeworkMapper;
import ru.otus.hw.models.Task;
import ru.otus.hw.repositories.HomeworkRepository;
import ru.otus.hw.repositories.TaskRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HomeworkServiceImpl implements HomeworkService {
    private final TaskRepository taskRepository;
    private final HomeworkRepository homeworkRepository;

    private final HomeworkMapper homeworkMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkInfoDto> findAll() {
        var homeworks = homeworkRepository.findAll();
        return homeworkMapper.toHomeworkInfoDtos(homeworks);
    }

    @Override
    @Transactional(readOnly = true)
    public HomeworkDto findById(long id) {
        var homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Homework with id %d not found".formatted(id)));
        return homeworkMapper.toDto(homework);
    }

    @Override
    @Transactional
    public HomeworkDto create(HomeworkDto homeworkDto) {
        homeworkDto.setId(0L);
        var homework = homeworkMapper.toEntity(homeworkDto, getTask(homeworkDto.getTaskDto().getId()));
        return homeworkMapper.toDto(homeworkRepository.save(homework));
    }

    @Override
    @Transactional
    public HomeworkDto update(HomeworkDto homeworkDto) {
        validate(homeworkDto.getId());
        var homework = homeworkMapper.toEntity(homeworkDto, getTask(homeworkDto.getTaskDto().getId()));
        return homeworkMapper.toDto(homeworkRepository.save(homework));
    }

    @Override
    @Transactional
    public void delete(long id) {
        validate(id);
        homeworkRepository.deleteById(id);
    }

    private void validate(long id) {
        var group = findById(id);
        if (group == null) {
            throw new EntityNotFoundException("Homework with id %d not found".formatted(id));
        }
    }

    private Task getTask(long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with id %d not found".formatted(taskId)));
    }

}