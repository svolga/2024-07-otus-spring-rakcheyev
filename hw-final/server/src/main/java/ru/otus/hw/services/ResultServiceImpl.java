package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.ResultDto;
import ru.otus.hw.dto.ResultInfoDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.ResultMapper;
import ru.otus.hw.models.Homework;
import ru.otus.hw.models.Rank;
import ru.otus.hw.models.Result;
import ru.otus.hw.models.User;
import ru.otus.hw.repositories.HomeworkRepository;
import ru.otus.hw.repositories.RankRepository;
import ru.otus.hw.repositories.ResultRepository;
import ru.otus.hw.repositories.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {
    private final UserRepository userRepository;
    private final HomeworkRepository homeworkRepository;
    private final RankRepository rankRepository;

    private final ResultRepository resultRepository;
    private final ResultMapper resultMapper;

    @Override
    @Transactional(readOnly = true)
    public ResultDto findById(long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result with id %d not found".formatted(id)));
        return resultMapper.toDto(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultInfoDto> findAll() {
        var results = resultRepository.findAll();
        return resultMapper.toResultInfoDtos(results);
    }

    @Override
    @Transactional
    public ResultDto create(ResultDto resultDto) {
        resultDto.setId(0L);
        Result result = resultMapper.toEntity(resultDto,
                getStudent(resultDto.getStudentDto().getId()),
                getHomework(resultDto.getHomeworkDto().getId()),
                getRank(resultDto.getRankDto().getId())
        );
        return resultMapper.toDto(resultRepository.save(result));
    }

    @Override
    @Transactional
    public ResultDto update(ResultDto resultDto) {
        validate(resultDto.getId());
        Result result = resultMapper.toEntity(resultDto,
                getStudent(resultDto.getStudentDto().getId()),
                getHomework(resultDto.getHomeworkDto().getId()),
                getRank(resultDto.getRankDto().getId())
        );
        return resultMapper.toDto(resultRepository.save(result));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        validate(id);
        resultRepository.deleteById(id);
    }

    private void validate(long id) {
        if (!resultRepository.existsById(id)) {
            throw new EntityNotFoundException("Result with id %d not found".formatted(id));
        }
    }

    private Homework getHomework(long homeworkId) {
        return homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new EntityNotFoundException("Homework with id %d not found".formatted(homeworkId)));
    }

    private Rank getRank(long rankId) {
        return rankRepository.findById(rankId)
                .orElseThrow(() -> new EntityNotFoundException("Rank with id %d not found".formatted(rankId)));
    }

    private User getStudent(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id %d not found".formatted(userId)));
    }

}
