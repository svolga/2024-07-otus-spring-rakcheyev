package ru.otus.hw.services;

import ru.otus.hw.dto.TaskDto;
import ru.otus.hw.dto.TaskInfoDto;

import java.util.List;

public interface TaskService {
    TaskDto findById(long id);

    List<TaskInfoDto> findAll();

    TaskDto create(TaskDto taskDto);

    TaskDto update(TaskDto taskDto);

    void deleteById(long id);
}
