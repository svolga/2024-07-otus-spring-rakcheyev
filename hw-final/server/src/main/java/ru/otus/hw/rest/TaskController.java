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
import ru.otus.hw.dto.TaskDto;
import ru.otus.hw.dto.TaskInfoDto;
import ru.otus.hw.services.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

/*    private final TaskService taskService;

    @GetMapping("/api/v1/task")
    public List<TaskInfoDto> getTasks() {
        return taskService.findAll();
    }

    @GetMapping("/api/v1/task/{id}")
    public TaskDto getTask(@PathVariable long id) {
        return taskService.findById(id);
    }

    @DeleteMapping("/api/v1/task/{id}")
    public void deleteTask(@PathVariable("id") long id) {
        taskService.deleteById(id);
    }

    @PostMapping("/api/v1/task")
    public TaskDto createTask(@Valid @RequestBody TaskDto task) {
        return taskService.create(task);
    }

    @PutMapping("/api/v1/task")
    public TaskDto updateTask(@Valid @RequestBody TaskDto task) {
        return taskService.update(task);
    }*/
}
