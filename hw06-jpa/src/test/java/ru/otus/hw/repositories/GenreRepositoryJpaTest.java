package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Genre должен ")
@DataJpaTest
@Import(JpaGenreRepository.class)
public class GenreRepositoryJpaTest {

    private static final long FIRST_GENRE_ID = 1L;
    private static final long SECOND_GENRE_ID = 2L;
    private static final long THIRD_GENRE_ID = 3L;
    private static final long FOURTH_GENRE_ID = 4L;
    private static final long FIFTH_GENRE_ID = 5L;
    private static final long SIXTH_GENRE_ID = 6L;

    private static final int GENRES_COUNT = 6;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

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

        var searchedGenres = genreRepository.findAllByIds(
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
                em.find(Genre.class, FIRST_GENRE_ID),
                em.find(Genre.class, SECOND_GENRE_ID),
                em.find(Genre.class, THIRD_GENRE_ID),
                em.find(Genre.class, FOURTH_GENRE_ID),
                em.find(Genre.class, FIFTH_GENRE_ID),
                em.find(Genre.class, SIXTH_GENRE_ID)
            );
    }

}