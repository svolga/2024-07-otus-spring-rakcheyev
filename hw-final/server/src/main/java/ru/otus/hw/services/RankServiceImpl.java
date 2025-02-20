package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.RankDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.RankMapper;
import ru.otus.hw.repositories.RankRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RankServiceImpl implements RankService {
    private final RankRepository rankRepository;

    private final RankMapper rankMapper;

    @Override
    public List<RankDto> findAll() {
        var ranks = rankRepository.findAll();
        return rankMapper.toDtos(ranks);
    }

    @Override
    public RankDto findById(long id) {
        var rank = rankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rank with id %d not found".formatted(id)));
        return rankMapper.toDto(rank);
    }

    @Override
    @Transactional
    public RankDto create(RankDto rankDto) {
        rankDto.setId(0L);
        var rank = rankMapper.toEntity(rankDto);
        return rankMapper.toDto(rankRepository.save(rank));
    }

    @Override
    @Transactional
    public RankDto update(RankDto rankDto) {
        validate(rankDto.getId());
        var rank = rankMapper.toEntity(rankDto);
        return rankMapper.toDto(rankRepository.save(rank));
    }

    @Override
    @Transactional
    public void delete(long id) {
        validate(id);
        rankRepository.deleteById(id);
    }

    private void validate(long id) {
        var rank = findById(id);
        if (rank == null) {
            throw new EntityNotFoundException("Rank with id %d not found".formatted(id));
        }
    }

}