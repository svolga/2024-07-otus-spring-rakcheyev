package ru.otus.hw.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ResultPageController {

    private final static String INDEX_FILE = "views/result/index";
    private final static String EDIT_FILE = "views/result/edit";

    @GetMapping("/result")
    public String index() {
        return INDEX_FILE;
    }

    @GetMapping("/result/create")
    public String create() {
        return EDIT_FILE;
    }

    @GetMapping("/result/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return EDIT_FILE;
    }
}
