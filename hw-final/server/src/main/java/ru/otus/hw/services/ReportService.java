package ru.otus.hw.services;

import ru.otus.hw.dto.ResultReport;

import java.util.List;

public interface ReportService {
    List<ResultReport> getBestResults();
    List<ResultReport> getMiddleScoreResults();
}
