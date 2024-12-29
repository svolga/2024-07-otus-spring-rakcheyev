package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/v1/author")
    public List<AuthorDto> getAuthors(){
        return authorService.findAll();
    }

    @GetMapping("/api/v1/author/{id}")
    public AuthorDto getAuthor(@PathVariable long id) {
        return authorService.findById(id);
    }

    @PostMapping("/api/v1/author")
    public AuthorDto createAuthor(@Valid @RequestBody AuthorDto author) {
        return authorService.create(author);
    }

    @PutMapping("/api/v1/author")
    public AuthorDto updateAuthor(@Valid @RequestBody AuthorDto author) {
        return authorService.update(author);
    }

    @DeleteMapping("/api/v1/author/{id}")
    public void deleteAuthor(@PathVariable("id") long id) {
        authorService.delete(id);
    }
}
