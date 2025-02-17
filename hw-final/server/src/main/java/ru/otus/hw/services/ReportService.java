package ru.otus.hw.services;

import ru.otus.hw.dto.ResultReportDto;

import java.util.List;

public interface ReportService {
    List<ResultReportDto> getBestResults();
    List<ResultReportDto> getMiddleScoreResults();
}
