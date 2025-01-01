package ru.otus.hw.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookInfoDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер Book должен ")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_TITLE = "Book_1";

    private static final String BOOK_TITLE_UPDATED = "BookTitle_Updated";

    private static final long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_NAME = "Genre`_1";

    private static final long SECOND_GENRE_ID = 2L;

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author_1";


    @Test
    @DisplayName("возвращать список книг")
    void shouldReturnCorrectBookList() throws Exception {

        var bookInfoDtos = List.of(
                new BookInfoDto(FIRST_BOOK_ID, FIRST_BOOK_TITLE, FIRST_AUTHOR_NAME, FIRST_GENRE_NAME)
        );

        when(bookService.findAll()).thenReturn(bookInfoDtos);

        mvc.perform(get("/api/v1/book"))
                .andExpect(content().json(mapper.writeValueAsString(bookInfoDtos)))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("возвращать книгу по id")
    void shouldReturnCorrectBookById() throws Exception {
        var bookDto = getFirstBook();
        given(bookService.findById(FIRST_BOOK_ID)).willReturn(bookDto);

        mvc.perform(get("/api/v1/book/{id}", FIRST_BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)))
                .andDo(print());
    }

    @DisplayName("создавать книгу")
    @Test
    void shouldCreateBook() throws Exception {
        BookDto bookDto = getFirstBook();
        BookDto expectedBookDto = new BookDto(bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthorDto(), bookDto.getGenreDtos());

        when(bookService.create(bookDto)).thenReturn(expectedBookDto);

        ResultActions response = mvc.perform(post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedBookDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("обновлять книгу")
    @Test
    void shouldUpdateBook() throws Exception {
        BookDto bookDto = getFirstBook();

        BookDto bookUpdatedDto = new BookDto(bookDto.getId(), BOOK_TITLE_UPDATED,
                bookDto.getAuthorDto(), bookDto.getGenreDtos());

        when(bookService.update(bookUpdatedDto)).thenReturn(bookDto);

        ResultActions response = mvc.perform(put("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("возвращать статус 404 при несуществующем id книги")
    @Test
    void shouldNotUpdateBookWIthNotValidBookId() throws Exception {
        long bookId = 100L;
        BookDto bookDto = getFirstBook();

        BookDto bookUpdatedDto = new BookDto(bookId, BOOK_TITLE_UPDATED, bookDto.getAuthorDto(),
                bookDto.getGenreDtos());

        when(bookService.update(bookUpdatedDto)).thenThrow(new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        ResultActions response = mvc.perform(put("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookUpdatedDto))
        );
        response.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("удалять книгу по id")
    void shouldCorrectDeleteBook() throws Exception {
        willDoNothing().given(bookService).deleteById(FIRST_BOOK_ID);
        ResultActions response = mvc.perform(delete("/api/v1/book/{id}", FIRST_BOOK_ID));
        response.andExpect(status().isOk())
                .andDo(print());
    }

    private static BookDto getFirstBook() {
        return new BookDto(FIRST_BOOK_ID, FIRST_BOOK_TITLE, getFirstAuthor(), getGenres());
    }

    private static AuthorDto getFirstAuthor() {
        return new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
    }

    private static Set<Long> getGenres() {
        return Set.of(FIRST_GENRE_ID, SECOND_GENRE_ID);
    }

}