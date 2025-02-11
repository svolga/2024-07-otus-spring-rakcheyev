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
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorService;

import java.util.List;

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

@DisplayName("Контроллер Author должен ")
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Author_1";

    private static final long SECOND_AUTHOR_ID = 2L;
    private static final String SECOND_AUTHOR_NAME = "Author_2";

    private static final String UPDATED_AUTHOR_NAME = "Updated Author";

    @Test
    @DisplayName("возвращать список авторов")
    void shouldReturnCorrectAuthorList() throws Exception {

        var authorDtos = List.of(
                new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME),
                new AuthorDto(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME)
        );

        when(authorService.findAll()).thenReturn(authorDtos);

        mvc.perform(get("/api/v1/author"))
                .andExpect(content().json(mapper.writeValueAsString(authorDtos)))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("возвращать автора по id")
    void shouldReturnCorrectAuthorById() throws Exception {
        var authorDto = new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        given(authorService.findById(FIRST_AUTHOR_ID)).willReturn(authorDto);

        mvc.perform(get("/api/v1/author/{id}", FIRST_AUTHOR_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(authorDto)))
                .andDo(print());
    }

    @DisplayName("создавать автора")
    @Test
    void shouldCreateAuthor() throws Exception {
        AuthorDto authorDto = getFirstAuthor();
        AuthorDto expectedAuthorDto = new AuthorDto(authorDto.getId(), authorDto.getFullName());

        when(authorService.create(authorDto)).thenReturn(expectedAuthorDto);

        ResultActions response = mvc.perform(post("/api/v1/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedAuthorDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("обновлять автора")
    @Test
    void shouldUpdateAuthor() throws Exception {
        AuthorDto authorDto = getFirstAuthor();
        AuthorDto authorUpdatedDto = new AuthorDto(authorDto.getId(), UPDATED_AUTHOR_NAME);

        when(authorService.update(authorUpdatedDto)).thenReturn(authorDto);

        ResultActions response = mvc.perform(put("/api/v1/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authorDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("возвращать статус 404 при несуществующем id автора")
    @Test
    void shouldNotUpdateAuthorWIthNotValidAuthorId() throws Exception {
        long authorId = 100L;
        AuthorDto authorUpdatedDto = new AuthorDto(authorId, UPDATED_AUTHOR_NAME);

        when(authorService.update(authorUpdatedDto)).thenThrow(new EntityNotFoundException("Author with id %d not found".formatted(authorId)));

        ResultActions response = mvc.perform(put("/api/v1/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authorUpdatedDto))
        );
        response.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("удалять автора по id")
    void shouldCorrectDeleteAuthor() throws Exception {

        willDoNothing().given(authorService).delete(FIRST_AUTHOR_ID);

        ResultActions response = mvc.perform(delete("/api/v1/author/{id}", FIRST_AUTHOR_ID));

        response.andExpect(status().isOk())
                .andDo(print());
    }

    private static AuthorDto getFirstAuthor() {
        return new AuthorDto(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
    }

}