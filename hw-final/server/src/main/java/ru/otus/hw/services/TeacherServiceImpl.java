package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.TeacherDto;
import ru.otus.hw.dto.TeacherShortDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.TeacherMapper;
import ru.otus.hw.repositories.TeacherRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherShortDto> findAll() {
        var teachers = teacherRepository.findAll();
        return teacherMapper.toShortDtos(teachers);
    }

    @Override
    public TeacherDto findById(long id) {
        var teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id %d not found".formatted(id)));
        return teacherMapper.toDto(teacher);
    }

    @Override
    @Transactional
    public TeacherDto create(TeacherDto teacherDto) {
        teacherDto.setId(0L);
        var teacher = teacherMapper.toEntity(teacherDto);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    @Override
    @Transactional
    public TeacherDto update(TeacherDto teacherDto) {
        validate(teacherDto.getId());
        var teacher = teacherMapper.toEntity(teacherDto);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    @Override
    @Transactional
    public void delete(long id) {
        validate(id);
        teacherRepository.deleteById(id);
    }

    private void validate(long id) {
        var genre = findById(id);
        if (genre == null) {
            throw new EntityNotFoundException("Teacher with id %d not found".formatted(id));
        }
    }
}
