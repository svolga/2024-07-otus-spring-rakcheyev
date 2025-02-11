package ru.otus.hw.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.TeacherDto;
import ru.otus.hw.dto.TeacherShortDto;
import ru.otus.hw.services.TeacherService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/api/v1/teacher")
    public List<TeacherShortDto> getTeachers(){
        return teacherService.findAll();
    }

    @GetMapping("/api/v1/teacher/{id}")
    public TeacherDto getTeacher(@PathVariable long id) {
        return teacherService.findById(id);
    }

    @PostMapping("/api/v1/teacher")
    public TeacherDto createTeacher(@Valid @RequestBody TeacherDto teacher) {
        return teacherService.create(teacher);
    }

    @PutMapping("/api/v1/teacher")
    public TeacherDto updateTeacher(@Valid @RequestBody TeacherDto teacher) {
        return teacherService.update(teacher);
    }

    @DeleteMapping("/api/v1/teacher/{id}")
    public void deleteTeacher(@PathVariable("id") long id) {
        teacherService.delete(id);
    }
}
