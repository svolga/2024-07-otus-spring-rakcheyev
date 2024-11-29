package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Book должен ")
@DataMongoTest
public class BookRepositoryJpaTest {

    private static final String FIRST_BOOK_ID = "1";
    private static final String CREATED_BOOK_ID = "4";

    private static final String FIRST_GENRE_ID = "1";
    private static final String SECOND_GENRE_ID = "2";
    private static final String THIRD_GENRE_ID = "3";
    private static final String FOURTH_GENRE_ID = "4";

    private static final String FIRST_AUTHOR_ID = "1";
    private static final String THIRD_AUTHOR_ID = "3";

    private static final String BOOK_TITLE_CREATED = "BookTitle_Created";
    private static final String BOOK_TITLE_UPDATED = "BookTitle_Updated";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    private Book createdBook;

    private Book updatedBook;

    @BeforeEach
    void setUp() {
        createdBook = getCreatedBook();
        updatedBook = getUpdatedBook();
    }

    @DisplayName("загружать книгу по id")
    @Test
    void shouldFindBookById() {
        val optionalActualBook = bookRepository.findById(FIRST_BOOK_ID);
        val expectedBook = mongoTemplate.findById(FIRST_BOOK_ID, Book.class);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("загружать список всех книг")
    @Test
    void shouldFindAllBooks() {
        var actualBooks = bookRepository.findAll();
        var expectedBooks = mongoTemplate.findAll(Book.class);

        actualBooks.forEach(System.out::println);
        expectedBooks.forEach(System.out::println);

        assertThat(actualBooks.size()).isEqualTo(expectedBooks.size());
        assertThat(actualBooks).hasOnlyElementsOfType(Book.class);
    }

    @DisplayName("сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = createdBook;
        var saveddBook = bookRepository.save(expectedBook);

        System.out.println("expectedBook = " + expectedBook);
        System.out.println("savedBook = " + saveddBook);

        assertThat(saveddBook).isNotNull()
                .matches(book -> Integer.parseInt(book.getId()) > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedBook);

        assertThat(mongoTemplate.findById(saveddBook.getId(), Book.class)).isEqualTo(saveddBook);

    }

    @DisplayName("сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = updatedBook;
        assertThat(mongoTemplate.findById(expectedBook.getId(), Book.class)).isNotEqualTo(expectedBook);

        var savedBook = bookRepository.save(expectedBook);
        assertThat(savedBook).isNotNull()
                .matches(book -> Integer.parseInt(book.getId()) > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedBook);

        assertThat(mongoTemplate.findById(savedBook.getId(), Book.class)).isEqualTo(savedBook);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBook() {
        assertThat(mongoTemplate.findById(FIRST_BOOK_ID, Book.class)).isNotNull();
        bookRepository.deleteById(FIRST_BOOK_ID);
        assertThat(mongoTemplate.findById(FIRST_BOOK_ID, Book.class)).isNull();
    }

    private Book getCreatedBook() {
        Author author = mongoTemplate.findById(THIRD_AUTHOR_ID, Author.class);
        List<Genre> genres = List.of(
                mongoTemplate.findById(THIRD_GENRE_ID, Genre.class),
                mongoTemplate.findById(FOURTH_GENRE_ID, Genre.class));
        return new Book(CREATED_BOOK_ID, BOOK_TITLE_CREATED, author, genres);
    }

    private Book getUpdatedBook() {
        Author author = mongoTemplate.findById(FIRST_AUTHOR_ID, Author.class);
        List<Genre> genres = List.of(
                mongoTemplate.findById(FIRST_GENRE_ID, Genre.class),
                mongoTemplate.findById(SECOND_GENRE_ID, Genre.class));
        return new Book(FIRST_BOOK_ID, BOOK_TITLE_UPDATED, author, genres);
    }

}