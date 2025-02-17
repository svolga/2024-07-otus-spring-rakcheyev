package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.TaskMaterialDto;
import ru.otus.hw.services.TaskMaterialService;

@RestController
@RequiredArgsConstructor
public class TaskMaterialController {

    private final TaskMaterialService taskMaterialService;

    @DeleteMapping("/api/v1/task-material/{id}")
    public void deleteTask(@PathVariable("id") long id) {
        taskMaterialService.deleteById(id);
    }

    @PostMapping("/api/v1/task-material")
    public TaskMaterialDto createTask(@Valid @RequestBody TaskMaterialDto taskMaterialDto) {
        return taskMaterialService.create(taskMaterialDto);
    }

}
