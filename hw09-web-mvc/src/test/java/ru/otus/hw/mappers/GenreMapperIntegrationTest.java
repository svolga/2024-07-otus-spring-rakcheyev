package ru.otus.hw.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Mapper Genre должен ")
public class GenreMapperIntegrationTest {

    private GenreMapper genreMapper = new GenreMapperImpl();

    private static final long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_NAME = "Genre_1";

    private static final long SECOND_GENRE_ID = 2L;
    private static final String SECOND_GENRE_NAME = "Genre_2";

    @Test
    @DisplayName("конвертировать в Dto")
    public void givenSourceToDestination_whenMaps_thenCorrect() {
        Genre sourceGenre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        GenreDto destination = genreMapper.toDto(sourceGenre);

        assertEquals(sourceGenre.getId(), destination.getId());
        assertEquals(sourceGenre.getName(), destination.getName());
    }

    @Test
    @DisplayName("конвертировать в Entity")
    public void givenDestinationToSource_whenMaps_thenCorrect() {
        GenreDto sourceGenre = new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        Genre destination = genreMapper.toEntity(sourceGenre);

        assertEquals(sourceGenre.getId(), destination.getId());
        assertEquals(sourceGenre.getName(), destination.getName());
    }

    @Test
    @DisplayName("конвертировать список Entity в Dto")
    public void givenSourceToDestinationDdtos_whenMaps_thenCorrect() {

        var actual = List.of(
                new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME),
                new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME)
        );

        var expected = List.of(
                new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME),
                new GenreDto(SECOND_GENRE_ID, SECOND_GENRE_NAME)
        );

        var destination = genreMapper.toDtos(actual);
        assertEquals(destination, expected);
    }

}