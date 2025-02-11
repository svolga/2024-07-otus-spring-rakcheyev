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
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookInfoDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/v1/book")
    public List<BookInfoDto> getBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/book/{id}")
    public BookDto getBook(@PathVariable long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/api/v1/book/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }

    @PostMapping("/api/v1/book")
    public BookDto createBook(@Valid @RequestBody BookDto book) {
        return bookService.create(book);
    }

    @PutMapping("/api/v1/book")
    public BookDto updateBook(@Valid @RequestBody BookDto book) {
        return bookService.update(book);
    }
}
