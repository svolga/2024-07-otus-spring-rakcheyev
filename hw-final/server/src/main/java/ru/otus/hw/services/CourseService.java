package ru.otus.hw.services;

import ru.otus.hw.dto.CourseDto;

import java.util.List;
import java.util.Set;

public interface CourseService {
    List<CourseDto> findAll();

    CourseDto findById(long id);

    List<CourseDto> findAllByIds(Set<Long> ids);

    CourseDto create(CourseDto courseDto);

    CourseDto update(CourseDto courseDto);

    void delete(long id);
}
