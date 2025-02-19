package ru.otus.hw.services;

import ru.otus.hw.dto.TaskMaterialDto;

public interface TaskMaterialService {

    TaskMaterialDto findById(long id);

    TaskMaterialDto create(TaskMaterialDto taskMaterialDto);

    void deleteById(long id);
}
