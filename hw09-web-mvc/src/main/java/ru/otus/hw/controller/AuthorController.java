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
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private final static String REDIRECT_INDEX = "redirect:/author";
    private final static String INDEX_FILE = "views/author/index";
    private final static String EDIT_FILE = "views/author/edit";

    @GetMapping("/author")
    public String indexPage(Model model) {
        List<AuthorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return INDEX_FILE;
    }

    @GetMapping("/author/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        AuthorDto author = authorService.findById(id);
        model.addAttribute("author", author);
        return EDIT_FILE;
    }

    @PostMapping("/author/edit")
    public String updateAuthor(@Valid @ModelAttribute("author") AuthorDto author,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return EDIT_FILE;
        }

        authorService.update(author);
        return REDIRECT_INDEX;
    }

    @PostMapping("/author/create")
    public String createAutor(@Valid @ModelAttribute("author") AuthorDto author,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return EDIT_FILE;
        }

        authorService.create(author);
        return REDIRECT_INDEX;
    }

    @GetMapping("/author/create")
    public String create(Model model) {
        model.addAttribute("author", new AuthorDto());
        return EDIT_FILE;
    }

    @GetMapping("/author/delete")
    public String deleteAutor(@RequestParam("id") long id) {
        authorService.delete(id);
        return REDIRECT_INDEX;
    }

}
