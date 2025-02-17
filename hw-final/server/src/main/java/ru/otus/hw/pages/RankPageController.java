package ru.otus.hw.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RankPageController {

    private final static String INDEX_FILE = "views/rank/index";
    private final static String EDIT_FILE = "views/rank/edit";

    @GetMapping("/rank")
    public String listRanksPage() {
        return INDEX_FILE;
    }

    @GetMapping("/rank/create")
    public String create() {
        return EDIT_FILE;
    }

    @GetMapping("/rank/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return EDIT_FILE;
    }
}