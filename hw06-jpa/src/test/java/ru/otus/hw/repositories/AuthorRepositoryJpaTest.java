package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Author должен ")
@DataJpaTest
@Import(JpaAuthorRepository.class)
public class AuthorRepositoryJpaTest {

    private static final int AUTHORS_COUNT = 3;

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long SECOND_AUTHOR_ID = 2L;
    private static final long THIRD_AUTHOR_ID = 3L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository authorRepository;

    private List<Author> authors;

    @BeforeEach
    void setUp() {
        authors = getAuthors();
    }

    @Test
    @DisplayName("возвращать список всех авторов")
    void shouldFindAllAuthors() {
        var actualAuthors = authorRepository.findAll();
        assertThat(authors)
                .hasSize(AUTHORS_COUNT)
                .usingRecursiveComparison()
                .isEqualTo(actualAuthors);

        actualAuthors.forEach(System.out::println);
    }

    @Test
    @DisplayName("находить автора по id")
    void shouldFindAuthorById() {
        var actualAuthor = authorRepository.findById(FIRST_AUTHOR_ID);
        assertThat(actualAuthor)
                .isNotEmpty()
                .get()
                .isEqualTo(authors.get(0));
    }

    private List<Author> getAuthors() {
        return List.of(
                em.find(Author.class, FIRST_AUTHOR_ID),
                em.find(Author.class, SECOND_AUTHOR_ID),
                em.find(Author.class, THIRD_AUTHOR_ID)
        );
    }
}
