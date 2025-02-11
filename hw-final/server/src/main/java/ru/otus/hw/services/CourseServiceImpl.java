package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CourseDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.CourseMapper;
import ru.otus.hw.repositories.CourseRepository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    @Override
    public List<CourseDto> findAll() {
        var courses = courseRepository.findAll();
        return courseMapper.toDtos(courses);
    }

    @Override
    public CourseDto findById(long id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course with id %d not found".formatted(id)));
        return courseMapper.toDto(course);
    }

    @Override
    public List<CourseDto> findAllByIds(Set<Long> ids) {
        var courses = courseRepository.findAllByIdIn(ids);
        return courseMapper.toDtos(courses);
    }

    @Override
    @Transactional
    public CourseDto create(CourseDto courseDto) {
        courseDto.setId(0L);
        var course = courseMapper.toEntity(courseDto);
        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    @Transactional
    public CourseDto update(CourseDto courseDto) {
        validate(courseDto.getId());
        var course = courseMapper.toEntity(courseDto);
        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void delete(long id) {
        validate(id);
        courseRepository.deleteById(id);
    }

    private void validate(long id) {
        var course = findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course with id %d not found".formatted(id));
        }
    }

}