package ru.otus.hw.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Mapper Book должен ")
@DataJpaTest
public class BookMapperIntegrationTest {

    private BookMapper bookMapper;

    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_TITLE = "BookTitle_1";

    private static final long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_NAME = "Genre_1";

    private static final long SECOND_GENRE_ID = 2L;
    private static final String SECOND_GENRE_NAME = "Genre_2";

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author_1";

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookMapper = Mappers.getMapper(BookMapper.class);
    }

    @Test
    @DisplayName("конвертировать в Dto")
    public void givenSourceToDestination_whenMaps_thenCorrect() {

        BookDto expectedBookDto = new BookDto(FIRST_BOOK_ID, FIRST_BOOK_TITLE,
                new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME),
                Set.of(FIRST_GENRE_ID, SECOND_GENRE_ID)
        );

        Book book = bookRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Not found "));
        BookDto actualBookDto = bookMapper.toDto(book);
        assertEquals(actualBookDto, expectedBookDto);
    }

    @Test
    @DisplayName("конвертировать в Entity")
    public void givenDestinationToSource_whenMaps_thenCorrect() {

        Book bookExpected = bookRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Not found "));

        BookDto expectedBookDto = new BookDto(FIRST_BOOK_ID, FIRST_BOOK_TITLE,
                new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME),
                Set.of(FIRST_GENRE_ID, SECOND_GENRE_ID)
        );

        Author author = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        List<Genre> genres = List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME),
                new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME));

        Book actualBook = bookMapper.toEntity(expectedBookDto, author, genres);

        assertEquals(bookExpected.getId(), actualBook.getId());
        assertEquals(bookExpected.getTitle(), actualBook.getTitle());
        assertEquals(bookExpected.getAuthor(), actualBook.getAuthor());
        assertEquals(bookExpected.getGenres().get(0), actualBook.getGenres().get(0));
        assertEquals(bookExpected.getGenres().get(1), actualBook.getGenres().get(1));
    }

}