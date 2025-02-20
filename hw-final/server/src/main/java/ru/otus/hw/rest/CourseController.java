package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.CourseDto;
import ru.otus.hw.services.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/api/v1/course")
    public List<CourseDto> getCourses(){
        return courseService.findAll();
    }

    @GetMapping("/api/v1/course/{id}")
    public CourseDto getCourse(@PathVariable long id) {
        return courseService.findById(id);
    }

    @PostMapping("/api/v1/course")
    public CourseDto createCourse(@Valid @RequestBody CourseDto course) {
        return courseService.create(course);
    }

    @PutMapping("/api/v1/course")
    public CourseDto updateCourse(@Valid @RequestBody CourseDto course) {
        return courseService.update(course);
    }

    @DeleteMapping("/api/v1/course/{id}")
    public void deleteCourse(@PathVariable("id") long id) {
        courseService.delete(id);
    }
}
