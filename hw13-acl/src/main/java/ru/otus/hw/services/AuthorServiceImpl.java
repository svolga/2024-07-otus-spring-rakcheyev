package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDto> findAll() {
        var authors = authorRepository.findAll();
        return authorMapper.toDtos(authors);
    }

    @Override
    public AuthorDto findById(long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(id)));
        return authorMapper.toDto(author);
    }

    @Override
    @Transactional
    public AuthorDto create(AuthorDto authorDto) {
        authorDto.setId(0L);
        var author = authorMapper.toEntity(authorDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public AuthorDto update(AuthorDto authorDto) {
        validate(authorDto.getId());
        var author = authorMapper.toEntity(authorDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Override
    @Transactional
    public void delete(long id) {
        validate(id);
        authorRepository.deleteById(id);
    }

    private void validate(long id) {
        var genre = findById(id);
        if (genre == null) {
            throw new EntityNotFoundException("Author with id %d not found".formatted(id));
        }
    }
}
