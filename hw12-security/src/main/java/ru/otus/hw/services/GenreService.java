package ru.otus.hw.services;

import ru.otus.hw.dto.GenreDto;

import java.util.List;
import java.util.Set;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long id);

    List<GenreDto> findAllByIds(Set<Long> ids);

    GenreDto create(GenreDto genreDto);

    GenreDto update(GenreDto genreDto);

    void delete(long id);
}
