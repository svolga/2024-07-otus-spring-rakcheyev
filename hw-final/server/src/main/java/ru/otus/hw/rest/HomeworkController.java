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
import ru.otus.hw.dto.HomeworkDto;
import ru.otus.hw.dto.HomeworkInfoDto;
import ru.otus.hw.services.HomeworkService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeworkController {
    private final HomeworkService homeworkService;

    @GetMapping("/api/v1/homework")
    public List<HomeworkInfoDto> getGroups() {
        return homeworkService.findAll();
    }

    @GetMapping("/api/v1/homework/{id}")
    public HomeworkDto getHomework(@PathVariable long id) {
        return homeworkService.findById(id);
    }

    @PostMapping("/api/v1/homework")
    public HomeworkDto createHomework(@Valid @RequestBody HomeworkDto homeworkDto) {
        return homeworkService.create(homeworkDto);
    }

    @PutMapping("/api/v1/homework")
    public HomeworkDto updateHomework(@Valid @RequestBody HomeworkDto homeworkDto) {
        return homeworkService.update(homeworkDto);
    }

    @DeleteMapping("/api/v1/homework/{id}")
    public void deleteHomework(@PathVariable("id") long id) {
        homeworkService.delete(id);
    }
}
