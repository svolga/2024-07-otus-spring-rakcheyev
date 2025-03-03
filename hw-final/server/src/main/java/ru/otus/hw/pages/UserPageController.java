package ru.otus.hw.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserPageController {

    private final static String INDEX_FILE = "views/user/index";
    private final static String INDEX_STUDENT_FILE = "views/student/index";
    private final static String INDEX_TEACHER_FILE = "views/teacher/index";
    private final static String EDIT_FILE = "views/user/edit";
    private final static String EDIT_TEACHER_FILE = "views/teacher/edit";

    @GetMapping("/user")
    public String index() {
        return INDEX_FILE;
    }

    @GetMapping("/student")
    public String indexStudents() {
        return INDEX_STUDENT_FILE;
    }

    @GetMapping("/teacher")
    public String indexTeachers() {
        return INDEX_TEACHER_FILE;
    }

    @GetMapping("/user/create")
    public String create() {
        return EDIT_FILE;
    }

    @GetMapping("/user/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return EDIT_FILE;
    }

    @GetMapping("/teacher/edit")
    public String editTeacherPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return EDIT_TEACHER_FILE;
    }
}
