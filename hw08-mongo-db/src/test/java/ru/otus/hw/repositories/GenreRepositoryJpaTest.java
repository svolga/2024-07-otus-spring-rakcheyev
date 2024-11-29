package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Genre должен ")
@DataMongoTest
public class GenreRepositoryJpaTest {

    private static final String FIRST_GENRE_ID = "1";
    private static final String SECOND_GENRE_ID = "2";
    private static final String THIRD_GENRE_ID = "3";
    private static final String FOURTH_GENRE_ID = "4";
    private static final String FIFTH_GENRE_ID = "5";
    private static final String SIXTH_GENRE_ID = "6";

    private static final int GENRES_COUNT = 6;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private List<Genre> genres;

    @BeforeEach
    void setUp(){
        genres = getGenres();
    }

    @DisplayName("возвращать список всех жанров")
    @Test
    void shouldFindAllGenres() {
        List<Genre> actualGenres = genreRepository.findAll();
        assertThat(actualGenres)
                .hasSize(GENRES_COUNT)
                .usingRecursiveComparison()
                .isEqualTo(genres);

        actualGenres.forEach(System.out::println);
    }

    @DisplayName("возвращать жанры по ids")
    @Test
    void shouldFindAllGenresByIds() {

        var searchedGenres = genreRepository.findAllByIdIn(
                Set.of(FIRST_GENRE_ID, SECOND_GENRE_ID, THIRD_GENRE_ID,
                        FOURTH_GENRE_ID, FIFTH_GENRE_ID, SIXTH_GENRE_ID));

        var expectedGenres = List.of(genres.get(0), genres.get(1), genres.get(2),
                        genres.get(3), genres.get(4), genres.get(5));

        assertThat(searchedGenres)
                .usingRecursiveComparison()
                .isEqualTo(expectedGenres);
    }

    private List<Genre> getGenres(){
        return List.of(
                mongoTemplate.findById(FIRST_GENRE_ID, Genre.class),
                mongoTemplate.findById(SECOND_GENRE_ID, Genre.class),
                mongoTemplate.findById(THIRD_GENRE_ID, Genre.class),
                mongoTemplate.findById(FOURTH_GENRE_ID, Genre.class),
                mongoTemplate.findById(FIFTH_GENRE_ID, Genre.class),
                mongoTemplate.findById(SIXTH_GENRE_ID, Genre.class)
            );
    }

}