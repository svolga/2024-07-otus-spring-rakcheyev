package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Author должен ")
@DataMongoTest
public class AuthorRepositoryJpaTest {

    private static final int AUTHORS_COUNT = 3;

    private static final String FIRST_AUTHOR_ID = "1";
    private static final String SECOND_AUTHOR_ID = "2";
    private static final String THIRD_AUTHOR_ID = "3";

    @Autowired
    private MongoTemplate mongoTemplate;

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
                mongoTemplate.findById(FIRST_AUTHOR_ID, Author.class),
                mongoTemplate.findById(SECOND_AUTHOR_ID, Author.class),
                mongoTemplate.findById(THIRD_AUTHOR_ID, Author.class)
        );
    }
}
