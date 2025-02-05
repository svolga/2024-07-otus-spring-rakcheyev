package ru.otus.hw.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookInfoDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.security.CustomUserDetailService;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Контроллер Book должен ")
@WebMvcTest(controllers = BookController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CustomUserDetailService customUserDetailService;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_TITLE = "BookTitle_1";

    private static final long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_NAME = "Genre_1";

    private static final long SECOND_GENRE_ID = 2L;
    private static final String SECOND_GENRE_NAME = "Genre_2";

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author_1";

    private static final long BOOK_ID_CREATED = 4L;

    private final static String REDIRECT_INDEX = "/";
    private final static String INDEX_FILE = "views/book/index";
    private final static String EDIT_FILE = "views/book/edit";

    private BookMapper bookMapper;

    @BeforeEach
    void setup() {
        bookMapper = Mappers.getMapper(BookMapper.class);
    }

    @Test
    @DisplayName("возвращать страницу со списком книг")
    void shouldReturnCorrectIndexPage() throws Exception {
        List<BookInfoDto> books = List.of(
                new BookInfoDto(FIRST_BOOK_ID, FIRST_BOOK_TITLE, FIRST_AUTHOR_NAME, FIRST_GENRE_NAME)
        );
        when(bookService.findAll()).thenReturn(books);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", books))
                .andExpect(view().name(INDEX_FILE));
    }

    @Test
    @DisplayName("открывать страницу создания книги")
    void shouldReturnCorrectCreateGetPage() throws Exception {
        var genres = List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME));
        var authors = List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME));

        when(genreService.findAll()).thenReturn(genres);
        when(authorService.findAll()).thenReturn(authors);

        mvc.perform(get("/book/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book", "genres", "authors"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(view().name(EDIT_FILE));
    }

    @Test
    @DisplayName("создавать книгу")
    void shouldReturnCorrectCreatePostPage() throws Exception {

        BookDto bookDto = new BookDto(FIRST_BOOK_ID, FIRST_BOOK_TITLE, getFirstAuthor(), getGenres());
        Book expectedBook = new Book(BOOK_ID_CREATED, FIRST_BOOK_TITLE, new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME),
                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME)));

        when(bookService.create(any(BookDto.class)))
                .thenReturn(bookMapper.toDto(expectedBook));

        mvc.perform(post("/book/create")
                        .with(csrf())
                        .flashAttr("book", bookDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REDIRECT_INDEX));
    }

    @Test
    @DisplayName("редактировать книгу")
    void shouldReturnCorrectUpdatePage() throws Exception {

        var genres = List.of(new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME));
        var authors = List.of(new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME));

        when(genreService.findAll()).thenReturn(genres);
        when(authorService.findAll()).thenReturn(authors);

        mvc.perform(get("/book/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book", "genres", "authors"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(view().name(EDIT_FILE));
    }

    @DisplayName("обновить книгу")
    @Test
    void shouldUpdateBook() throws Exception {

        BookDto bookDto = new BookDto(FIRST_BOOK_ID, FIRST_BOOK_TITLE, getFirstAuthor(), getGenres());

        Book book = new Book(FIRST_BOOK_ID, FIRST_BOOK_TITLE,
                new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME),
                List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME), new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME))
        );

        when(bookService.update(bookDto)).thenReturn(bookMapper.toDto(book));

        mvc.perform(post("/book/edit").flashAttr("book", bookDto)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REDIRECT_INDEX));
    }

    @Test
    void deleteBook() throws Exception {
        doNothing().when(bookService).deleteById(anyLong());

        mvc.perform(post("/book/delete")
                        .with(csrf())
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REDIRECT_INDEX));
    }

    private static AuthorDto getFirstAuthor() {
        return new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
    }

    private static Set<Long> getGenres() {
        return Set.of(FIRST_GENRE_ID, SECOND_GENRE_ID);
    }

}