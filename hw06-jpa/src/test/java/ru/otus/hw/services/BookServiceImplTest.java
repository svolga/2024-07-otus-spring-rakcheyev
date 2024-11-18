package ru.otus.hw.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.JpaAuthorRepository;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaGenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис Book должен ")
@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({BookServiceImpl.class, JpaBookRepository.class, JpaAuthorRepository.class, JpaGenreRepository.class})
public class BookServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;
    private static final int EXPECTED_NUMBER_OF_GENRES_OF_FIRST_BOOK = 2;

    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_TITLE = "BookTitle_1";

    private static final long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_NAME = "Genre_1";

    private static final long SECOND_GENRE_ID = 2L;
    private static final String SECOND_GENRE_NAME = "Genre_2";

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author_1";

    private static final long BOOK_ID_CREATED = 4L;

    private static final String BOOK_TITLE_CREATED = "BookTitle_Created";
    private static final String BOOK_TITLE_UPDATED = "BookTitle_Updated";

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("загружать книгу по id")
    public void shouldFindBookById() {

        Optional<Book> actualBook = bookService.findById(FIRST_BOOK_ID);
        var expectedBook = getFirstBook();

        assertThat(actualBook.get()).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = bookService.findAll();

        assertThat(actualBooks).isNotEmpty()
                .hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .hasOnlyElementsOfType(Book.class);

        assertThat(actualBooks.get(0).getAuthor().getId()).isEqualTo(FIRST_AUTHOR_ID);
        assertThat(actualBooks.get(0).getGenres().size()).isEqualTo(EXPECTED_NUMBER_OF_GENRES_OF_FIRST_BOOK);
    }

    @DisplayName("сохранять новую книгу")
    @Test
    void shouldSaveCreatedBook() {
        var expectedBook = getNewBookData();
        var actualBook = bookService.insert(expectedBook.getTitle(), expectedBook.getAuthor().getId(),
                expectedBook.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()));
        assertThat(actualBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = getFirstBook();
        expectedBook.setTitle(BOOK_TITLE_UPDATED);

        assertThat(bookService.findById(expectedBook.getId()))
                .isPresent();

        assertThat(bookService.findById(expectedBook.getId()).get().getTitle()).isNotEqualTo(expectedBook.getTitle());

        var actualBook = bookService.update(FIRST_BOOK_ID, expectedBook.getTitle(), expectedBook.getAuthor().getId(),
                expectedBook.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()));
        assertThat(actualBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedBook);

        assertThat(bookService.findById(actualBook.getId()))
                .isPresent();
    }

    @DisplayName("удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertThat(bookService.findById(FIRST_BOOK_ID)).isPresent();
        bookService.deleteById(FIRST_BOOK_ID);

        var actualBook = bookService.findById(FIRST_BOOK_ID);
        assertThat(actualBook).isEmpty();
    }

    private static Book getFirstBook() {
        return new Book(FIRST_BOOK_ID, FIRST_BOOK_TITLE, getFirstAuthor(), getGenres());
    }

    private static Book getNewBookData() {
        return new Book(BOOK_ID_CREATED, BOOK_TITLE_CREATED, getFirstAuthor(), getGenres());
    }

    private static Author getFirstAuthor() {
        return new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
    }

    private static List<Genre> getGenres() {
        return List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME), new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME));
    }
}
