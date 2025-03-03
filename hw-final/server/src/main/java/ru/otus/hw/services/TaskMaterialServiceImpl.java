package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.TaskMaterialDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.TaskMaterialMapper;
import ru.otus.hw.models.Task;
import ru.otus.hw.models.TaskMaterial;
import ru.otus.hw.repositories.TaskMaterialRepository;
import ru.otus.hw.repositories.TaskRepository;

@RequiredArgsConstructor
@Service
public class TaskMaterialServiceImpl implements TaskMaterialService {

    private final TaskMaterialRepository taskMaterialRepository;
    private final TaskRepository taskRepository;
    private final TaskMaterialMapper taskMaterialMapper;

    @Override
    @Transactional
    public TaskMaterialDto create(TaskMaterialDto taskMaterialDto) {
        taskMaterialDto.setId(0L);
        Task task = taskRepository.findById(taskMaterialDto.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Task  with id %d not found".formatted(taskMaterialDto.getTaskId())));

        TaskMaterial taskMaterial = taskMaterialMapper.toEntity(taskMaterialDto, task);

        return taskMaterialMapper.toDto(taskMaterialRepository.save(taskMaterial));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        taskMaterialRepository.deleteById(id);
    }

    @Override
    public TaskMaterialDto findById(long id) {
        TaskMaterial taskMaterial = taskMaterialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task material with id %d not found".formatted(id)));
        return taskMaterialMapper.toDto(taskMaterial);
    }

}
