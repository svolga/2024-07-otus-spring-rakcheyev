package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookInfoDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    private final static String REDIRECT_INDEX = "redirect:/";
    private final static String INDEX_FILE = "views/book/index";
    private final static String EDIT_FILE = "views/book/edit";

    @GetMapping("/")
    public String indexPage(Model model) {
        List<BookInfoDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return INDEX_FILE;
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDto book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return EDIT_FILE;
    }

    @PostMapping("/book/edit")
    public String updateBook(@Valid @ModelAttribute("book") BookDto book,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("authors", authorService.findAll());
            return EDIT_FILE;
        }

        bookService.update(book);
        return REDIRECT_INDEX;
    }

    @PostMapping("/book/create")
    public String createBook(@Valid @ModelAttribute("book") BookDto book,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("authors", authorService.findAll());
            return EDIT_FILE;
        }

        bookService.create(book);
        return REDIRECT_INDEX;
    }


    @GetMapping("/book/create")
    public String create(Model model) {
        model.addAttribute("book", new BookDto());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return EDIT_FILE;
    }

    @GetMapping("/book/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return REDIRECT_INDEX;
    }

}
