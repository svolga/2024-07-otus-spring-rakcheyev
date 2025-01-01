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
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/api/v1/genre")
    public List<GenreDto> getGenres(){
        return genreService.findAll();
    }

    @GetMapping("/api/v1/genre/{id}")
    public GenreDto getGenre(@PathVariable long id) {
        return genreService.findById(id);
    }

    @PostMapping("/api/v1/genre")
    public GenreDto createGenre(@Valid @RequestBody GenreDto genre) {
        return genreService.create(genre);
    }

    @PutMapping("/api/v1/genre")
    public GenreDto updateGenre(@Valid @RequestBody GenreDto genre) {
        return genreService.update(genre);
    }

    @DeleteMapping("/api/v1/genre/{id}")
    public void deleteGenre(@PathVariable("id") long id) {
        genreService.delete(id);
    }
}
