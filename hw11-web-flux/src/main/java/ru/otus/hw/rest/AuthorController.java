package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.repositories.AuthorRepository;

import static org.reflections.Reflections.log;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @GetMapping("/api/v1/author")
    public Flux<AuthorDto> getAuthors() {
        return authorRepository.findAll().map(authorMapper::toDto);
    }

    @GetMapping("/api/v1/author/{id}")
    public Mono<ResponseEntity<AuthorDto>> getAuthor(@PathVariable String id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping("/api/v1/author")
    public Mono<AuthorDto> createAuthor(@Valid @RequestBody AuthorDto author) {
        author.setId(null);
        return authorRepository.save(authorMapper.toEntity(author))
                .map(authorMapper::toDto);
    }

    @PutMapping("/api/v1/author")
    public Mono<ResponseEntity<AuthorDto>> updateAuthor(@Valid @RequestBody AuthorDto authorDto) {

        return authorRepository.existsById(authorDto.getId())
                .thenReturn(authorDto)
                .flatMap(authorDto1 ->
                        authorRepository
                                .save(authorMapper.toEntity(authorDto1)))
                .map(author -> new ResponseEntity<>(authorMapper.toDto(author), HttpStatus.OK))
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/api/v1/author/{id}")
    public Mono<ResponseEntity<String>> deleteAuthor(@PathVariable("id") String id) {
        return authorRepository.findById(id)
                .flatMap(author -> authorRepository.deleteById(id).thenReturn(author))
                .map(author -> {
                    log.info("Deleted author: {}", author);
                    return ResponseEntity.status(HttpStatus.OK).body("Author: " + author.getFullName() + " deleted!");
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("authorId: " + id + " Not found"));
    }

}
