package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GenreController {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @GetMapping("/api/v1/genre")
    public Flux<GenreDto> getGenres() {
        return genreRepository.findAll().map(genreMapper::toDto);
    }

    @GetMapping("/api/v1/genre/{id}")
    public Mono<ResponseEntity<GenreDto>> getGenre(@PathVariable String id) {
        return genreRepository.findById(id)
                .map(genreMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping("/api/v1/genre")
    public Mono<GenreDto> createGenre(@Valid @RequestBody GenreDto genre) {
        genre.setId(null);
        return genreRepository.save(genreMapper.toEntity(genre))
                .map(genreMapper::toDto);
    }

    @PutMapping("/api/v1/genre")
    public Mono<ResponseEntity<GenreDto>> updateGenre(@Valid @RequestBody GenreDto dto) {
        var existBooks = bookRepository.existsBookByGenresIdIn(List.of(dto.getId()));
        var existGenre = genreRepository.existsById(dto.getId());
        return Mono.zip(existGenre, existBooks)
                .filter(t -> t.getT1() && !t.getT2())
                .flatMap(t -> genreRepository.save(genreMapper.toEntity(dto)))
                .map(genre -> new ResponseEntity<>(genreMapper.toDto(genre), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @DeleteMapping("/api/v1/genre/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteGenre(@PathVariable("id") String id) {
        return bookRepository.existsBookByGenresIdIn(List.of(id))
                .filter(isExists -> !isExists)
                .flatMap(isNotExists -> genreRepository.deleteById(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

}
