package ru.otus.hw.pages;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReportPageController {

    private final static String INDEX_MIDDLE_FILE = "views/report/middle";
    private final static String INDEX_BEST_FILE = "views/report/best";

    @GetMapping("/report-best")
    public String indexStudents() {
        return INDEX_BEST_FILE;
    }

    @GetMapping("/report-middle")
    public String indexTeachers() {
        return INDEX_MIDDLE_FILE;
    }

}
