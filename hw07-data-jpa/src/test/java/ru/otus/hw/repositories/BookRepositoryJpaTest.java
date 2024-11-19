package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Book должен ")
@DataJpaTest
public class BookRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;

    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_TITLE = "BookTitle_1";

    private static final long FIRST_GENRE_ID = 1L;
    private static final long SECOND_GENRE_ID = 2L;
    private static final long THIRD_GENRE_ID = 3L;
    private static final long FOURTH_GENRE_ID = 4L;

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long THIRD_AUTHOR_ID = 3L;

    private static final String BOOK_TITLE_CREATED = "BookTitle_Created";
    private static final String BOOK_TITLE_UPDATED = "BookTitle_Updated";

    @Autowired
    private TestEntityManager em;

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
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("загружать список всех книг")
    @Test
    void shouldFindAllBooks() {
        var books = bookRepository.findAll();
        assertThat(books).hasSize(EXPECTED_NUMBER_OF_BOOKS);
        assertThat(books.get(0).getTitle()).isEqualTo(FIRST_BOOK_TITLE);
    }

    @DisplayName("сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = createdBook;
        var saveddBook = bookRepository.save(expectedBook);
        assertThat(saveddBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedBook);

        assertThat(em.find(Book.class, saveddBook.getId())).isEqualTo(saveddBook);
    }

    @DisplayName("сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = updatedBook;
        assertThat(em.find(Book.class, expectedBook.getId())).isNotEqualTo(expectedBook);

        var savedBook = bookRepository.save(expectedBook);
        assertThat(savedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedBook);

        assertThat(em.find(Book.class, savedBook.getId())).isEqualTo(savedBook);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBook() {
        assertThat(em.find(Book.class, FIRST_BOOK_ID)).isNotNull();
        bookRepository.deleteById(FIRST_BOOK_ID);
        assertThat(em.find(Book.class, FIRST_BOOK_ID)).isNull();
    }

    private Book getCreatedBook() {
        Author author = em.find(Author.class, THIRD_AUTHOR_ID);
        List<Genre> genres = List.of(
                em.find(Genre.class, THIRD_GENRE_ID),
                em.find(Genre.class, FOURTH_GENRE_ID));
        return new Book(0, BOOK_TITLE_CREATED, author, genres);
    }

    private Book getUpdatedBook() {
        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        List<Genre> genres = List.of(
                em.find(Genre.class, FIRST_GENRE_ID),
                em.find(Genre.class, SECOND_GENRE_ID));
        return new Book(FIRST_BOOK_ID, BOOK_TITLE_UPDATED, author, genres);
    }

}