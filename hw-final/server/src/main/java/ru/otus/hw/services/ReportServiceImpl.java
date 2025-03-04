package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.ResultReport;
import ru.otus.hw.repositories.ResultRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final ResultRepository resultRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ResultReport> getBestResults() {
        return resultRepository.getBestResults();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultReport> getMiddleScoreResults() {
        return resultRepository.getMiddleScoreResults();
    }
}
