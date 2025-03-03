package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Course;

import java.util.List;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIdIn(Set<Long> ids);
}