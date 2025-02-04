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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;

import static org.reflections.Reflections.log;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final BookRepository bookRepository;
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
    public Mono<ResponseEntity<AuthorDto>> updateAuthor(@Valid @RequestBody AuthorDto dto) {

        return Mono.just(dto)
                .filterWhen(authorDto -> authorRepository.existsById(authorDto.getId()))
                .flatMap(
                        authorDto -> authorRepository.save(authorMapper.toEntity(authorDto))
                                .and(
                                        bookRepository.findAllBooksByAuthorIdIn(List.of(dto.getId()))
                                                .flatMap(book -> {
                                                    book.setAuthor(authorMapper.toEntity(dto));
                                                    return bookRepository.save(book);
                                                })
                                )
                                .thenReturn(new ResponseEntity<>(authorDto, HttpStatus.OK))
                )
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/api/v1/author/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable("id") String id) {

        return bookRepository.existsBookByAuthorIdIn(List.of(id))
                .filter(isExists -> !isExists)
                .flatMap(isExists ->
                        authorRepository.deleteById(id)
                                .and(bookRepository.deleteAllBooksByAuthorId(id))
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));

    }

}
