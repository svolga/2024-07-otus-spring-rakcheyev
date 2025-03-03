package ru.otus.hw.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GroupPageController {

    private final static String INDEX_FILE = "views/group/index";
    private final static String EDIT_FILE = "views/group/edit";

    @GetMapping("/group")
    public String listGroupsPage() {
        return INDEX_FILE;
    }

    @GetMapping("/group/create")
    public String create() {
        return EDIT_FILE;
    }

    @GetMapping("/group/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return EDIT_FILE;
    }
}