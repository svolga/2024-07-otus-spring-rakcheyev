package ru.otus.hw.services;

import ru.otus.hw.dto.TeacherDto;
import ru.otus.hw.dto.TeacherShortDto;

import java.util.List;

public interface TeacherService {
    List<TeacherShortDto> findAll();
    
    TeacherDto findById(long id);

    TeacherDto create(TeacherDto teacherDto);

    TeacherDto update(TeacherDto teacherDto);

    void delete(long id);
}
