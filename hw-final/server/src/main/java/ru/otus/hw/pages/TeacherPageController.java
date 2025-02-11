package ru.otus.hw.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TeacherPageController {

    private final static String INDEX_FILE = "views/teacher/index";
    private final static String EDIT_FILE = "views/teacher/edit";

    @GetMapping("/teacher")
    public String listAuthorsPage() {
        return INDEX_FILE;
    }

    @GetMapping("/teacher/create")
    public String create() {
        return EDIT_FILE;
    }

    @GetMapping("/teacher/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return EDIT_FILE;
    }
}