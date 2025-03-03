package ru.otus.hw.services;

import ru.otus.hw.dto.RankDto;

import java.util.List;

public interface RankService {
    List<RankDto> findAll();

    RankDto findById(long id);

    RankDto create(RankDto rankDto);

    RankDto update(RankDto rankDto);

    void delete(long id);
}
