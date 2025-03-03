package ru.otus.hw.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TaskPageController {

    private final static String INDEX_FILE = "views/task/index";
    private final static String EDIT_FILE = "views/task/edit";

    @GetMapping("/task")
    public String index() {
        return INDEX_FILE;
    }

    @GetMapping("/task/create")
    public String create() {
        return EDIT_FILE;
    }

    @GetMapping("/task/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return EDIT_FILE;
    }
}
