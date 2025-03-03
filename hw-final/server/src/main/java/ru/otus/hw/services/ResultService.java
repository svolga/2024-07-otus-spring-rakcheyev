package ru.otus.hw.services;

import ru.otus.hw.dto.ResultDto;
import ru.otus.hw.dto.ResultInfoDto;

import java.util.List;

public interface ResultService {
    ResultDto findById(long id);

    List<ResultInfoDto> findAll();

    ResultDto create(ResultDto resultDto);

    ResultDto update(ResultDto resultDto);

    void deleteById(long id);
}
