package ru.otus.hw.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeworkPageController {

    private final static String INDEX_FILE = "views/homework/index";
    private final static String EDIT_FILE = "views/homework/edit";

    @GetMapping("/homework")
    public String listHomeworksPage() {
        return INDEX_FILE;
    }

    @GetMapping("/homework/create")
    public String create() {
        return EDIT_FILE;
    }

    @GetMapping("/homework/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return EDIT_FILE;
    }
}