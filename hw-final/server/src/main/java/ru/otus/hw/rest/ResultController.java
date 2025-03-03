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
import ru.otus.hw.dto.ResultDto;
import ru.otus.hw.dto.ResultInfoDto;
import ru.otus.hw.services.ResultService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/api/v1/result")
    public List<ResultInfoDto> getResults() {
        return resultService.findAll();
    }

    @GetMapping("/api/v1/result/{id}")
    public ResultDto getResult(@PathVariable long id) {
        return resultService.findById(id);
    }

    @DeleteMapping("/api/v1/result/{id}")
    public void deleteResult(@PathVariable("id") long id) {
        resultService.deleteById(id);
    }

    @PostMapping("/api/v1/result")
    public ResultDto createResult(@Valid @RequestBody ResultDto result) {
        return resultService.create(result);
    }

    @PutMapping("/api/v1/result")
    public ResultDto updateResult(@Valid @RequestBody ResultDto result) {
        return resultService.update(result);
    }
}
