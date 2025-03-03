package ru.otus.hw.services;

import ru.otus.hw.dto.HomeworkDto;
import ru.otus.hw.dto.HomeworkInfoDto;

import java.util.List;

public interface HomeworkService {
    List<HomeworkInfoDto> findAll();

    HomeworkDto findById(long id);

    HomeworkDto create(HomeworkDto homeworkDto);

    HomeworkDto update(HomeworkDto homeworkDto);

    void delete(long id);
}
