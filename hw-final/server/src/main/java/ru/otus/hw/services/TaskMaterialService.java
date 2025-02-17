package ru.otus.hw.services;

import ru.otus.hw.dto.TaskDto;
import ru.otus.hw.dto.TaskMaterialDto;

import java.util.List;

public interface TaskMaterialService {

    TaskMaterialDto findById(long id);

    TaskMaterialDto create(TaskMaterialDto taskMaterialDto);

    void deleteById(long id);
}
