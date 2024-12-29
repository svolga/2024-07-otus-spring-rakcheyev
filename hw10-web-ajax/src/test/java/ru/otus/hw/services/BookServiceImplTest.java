package ru.otus.hw.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookInfoDto;
import ru.otus.hw.mappers.AuthorMapperImpl;
import ru.otus.hw.mappers.BookMapperImpl;
import ru.otus.hw.mappers.GenreMapperImpl;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Сервис Book должен ")
@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({BookServiceImpl.class, BookMapperImpl.class, AuthorMapperImpl.class, GenreMapperImpl.class})
public class BookServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;

    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_TITLE = "BookTitle_1";

    private static final long FIRST_GENRE_ID = 1L;
    private static final long SECOND_GENRE_ID = 2L;

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author_1";

    private static final String BOOK_TITLE_CREATED = "BookTitle_Created";
    private static final String BOOK_TITLE_UPDATED = "BookTitle_Updated";

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("загружать книгу по id")
    public void shouldFindBookById() {
        var actualBook = bookService.findById(FIRST_BOOK_ID);
        var expectedBook = getFirstBook();

        assertThat(actualBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = bookService.findAll();

        assertThat(actualBooks).isNotEmpty()
                .hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .hasOnlyElementsOfType(BookInfoDto.class);

        assertThat(actualBooks.get(0).getAuthorTitle()).isEqualTo(FIRST_AUTHOR_NAME);
    }

    @DisplayName("сохранять новую книгу")
    @Test
    void shouldSaveCreatedBook() {
        var expectedBook = getNewBookData();
        var actualBook = bookService.create(expectedBook);
        expectedBook.setId(4L);
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
                . isNotNull();

        assertThat(bookService.findById(expectedBook.getId()).getTitle()).isNotEqualTo(expectedBook.getTitle());

        var actualBook = bookService.update(expectedBook);
        assertThat(actualBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedBook);

        assertThat(bookService.findById(actualBook.getId()))
                .isNotNull();

    }

    @DisplayName("удалять книгу по id ")
    @Test
    void shouldDeleteBook() {

        assertThat(bookService.findById(FIRST_BOOK_ID)).isNotNull();
        bookService.deleteById(FIRST_BOOK_ID);

        assertThatThrownBy(() -> bookService.findById(FIRST_BOOK_ID)).isInstanceOf(Exception.class)
                .hasMessage("Book with id 1 not found");
    }

    private static BookDto getFirstBook() {
        return new BookDto(FIRST_BOOK_ID, FIRST_BOOK_TITLE, getFirstAuthor(), getGenres());
    }

    private static BookDto getNewBookData() {
        return new BookDto(0L, BOOK_TITLE_CREATED, getFirstAuthor(), getGenres());
    }

    private static AuthorDto getFirstAuthor() {
        return new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
    }

    private static Set<Long> getGenres() {
        return Set.of(FIRST_GENRE_ID, SECOND_GENRE_ID);
    }

}