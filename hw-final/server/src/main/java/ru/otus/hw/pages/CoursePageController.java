package ru.otus.hw.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CoursePageController {

    private final static String INDEX_FILE = "views/course/index";
    private final static String EDIT_FILE = "views/course/edit";

    @GetMapping("/course")
    public String listCoursesPage() {
        return INDEX_FILE;
    }

    @GetMapping("/course/create")
    public String create() {
        return EDIT_FILE;
    }

    @GetMapping("/course/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return EDIT_FILE;
    }
}