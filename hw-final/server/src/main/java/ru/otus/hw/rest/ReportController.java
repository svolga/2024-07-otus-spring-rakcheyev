package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.ResultReport;
import ru.otus.hw.services.ReportService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/api/v1/best")
    public List<ResultReport> getBestResults() {
        return reportService.getBestResults();
    }

    @GetMapping("/api/v1/middle")
    public List<ResultReport> getMiddleScoreResults() {
        return reportService.getMiddleScoreResults();
    }

}
