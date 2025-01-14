package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
//    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Override
    public List<GenreDto> findAll() {
  /*
        var genres = genreRepository.findAll();
        return genreMapper.toDtos(genres);
        */
        return null;
    }

    @Override
    public GenreDto findById(long id) {
        /*
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(id)));
        return genreMapper.toDto(genre);
        */
        return null;
    }

    @Override
    public List<GenreDto> findAllByIds(Set<Long> ids) {
/*
        var genres = genreRepository.findAllByIdIn(ids);
        return genreMapper.toDtos(genres);
*/
        return null;
    }

    @Override
    @Transactional
    public GenreDto create(GenreDto genreDto) {

        /*genreDto.setId(0L);
        var genre = genreMapper.toEntity(genreDto);
        return genreMapper.toDto(genreRepository.save(genre));
        */
        return null;
    }

    @Override
    @Transactional
    public GenreDto update(GenreDto genreDto) {

/*
        validate(genreDto.getId());
        var genre = genreMapper.toEntity(genreDto);
        return genreMapper.toDto(genreRepository.save(genre));
*/
        return null;
    }

    @Override
    @Transactional
    public void delete(long id) {
        /*validate(id);
        genreRepository.deleteById(id);
        */
    }

    private void validate(long id) {
        var genre = findById(id);
        if (genre == null) {
            throw new EntityNotFoundException("Genre with id %d not found".formatted(id));
        }
    }

}
