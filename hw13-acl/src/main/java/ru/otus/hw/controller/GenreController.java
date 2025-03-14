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
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    private final static String REDIRECT_INDEX = "redirect:/genre";
    private final static String INDEX_FILE = "views/genre/index";
    private final static String EDIT_FILE = "views/genre/edit";

    @GetMapping("/genre")
    public String indexPage(Model model) {
        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return INDEX_FILE;
    }

    @GetMapping("/genre/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        GenreDto genre = genreService.findById(id);
        model.addAttribute("genre", genre);
        return EDIT_FILE;
    }

    @PostMapping("/genre/edit")
    public String updateGenre(@Valid @ModelAttribute("genre") GenreDto genre,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return EDIT_FILE;
        }

        genreService.update(genre);
        return REDIRECT_INDEX;
    }

    @PostMapping("/genre/create")
    public String createGenre(@Valid @ModelAttribute("genre") GenreDto genre,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return EDIT_FILE;
        }

        genreService.create(genre);
        return REDIRECT_INDEX;
    }


    @GetMapping("/genre/create")
    public String create(Model model) {
        model.addAttribute("genre", new GenreDto());
        return EDIT_FILE;
    }

    @PostMapping("/genre/delete")
    public String deleteGenre(@RequestParam("id") long id) {
        genreService.delete(id);
        return REDIRECT_INDEX;
    }
}
