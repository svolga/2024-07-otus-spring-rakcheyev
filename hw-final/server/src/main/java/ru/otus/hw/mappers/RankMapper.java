package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.hw.dto.RankDto;
import ru.otus.hw.models.Rank;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RankMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "ukey", target = "ukey")
    @Mapping(source = "color", target = "color")
    RankDto toDto(Rank rank);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "ukey", target = "ukey")
    @Mapping(source = "color", target = "color")
    Rank toEntity(RankDto rank);

    List<RankDto> toDtos(List<Rank> ranks);
    List<Rank> toEntity(List<RankDto> rankDtos);
}
