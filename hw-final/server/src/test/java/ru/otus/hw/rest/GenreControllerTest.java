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
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.GenreService;

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

@DisplayName("Контроллер Genre должен ")
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private static final long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_NAME = "Genre_1";

    private static final long SECOND_GENRE_ID = 2L;
    private static final String SECOND_GENRE_NAME = "Genre_2";

    private static final String UPDATED_GENRE_NAME = "Updated Genre";

    @Test
    @DisplayName("возвращать список жанров")
    void shouldReturnCorrectGenreList() throws Exception {

        var genreDtos = List.of(
                new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME),
                new GenreDto(SECOND_GENRE_ID, SECOND_GENRE_NAME)
        );

        when(genreService.findAll()).thenReturn(genreDtos);

        mvc.perform(get("/api/v1/genre"))
                .andExpect(content().json(mapper.writeValueAsString(genreDtos)))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("возвращать жанр по id")
    void shouldReturnCorrectGenreById() throws Exception {
        var genreDto = new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        given(genreService.findById(FIRST_GENRE_ID)).willReturn(genreDto);

        mvc.perform(get("/api/v1/genre/{id}", FIRST_GENRE_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genreDto)))
                .andDo(print());
    }

    @DisplayName("создавать жанр")
    @Test
    void shouldCreateAuthor() throws Exception {
        GenreDto genreDto = getFirstGenre();
        GenreDto expectedGenreDto = new GenreDto(genreDto.getId(), genreDto.getName());

        when(genreService.create(genreDto)).thenReturn(expectedGenreDto);

        ResultActions response = mvc.perform(post("/api/v1/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedGenreDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("обновлять жанр")
    @Test
    void shouldUpdateAuthor() throws Exception {
        GenreDto genreDto = getFirstGenre();
        GenreDto genreUpdatedDto = new GenreDto(genreDto.getId(), UPDATED_GENRE_NAME);

        when(genreService.update(genreUpdatedDto)).thenReturn(genreDto);

        ResultActions response = mvc.perform(put("/api/v1/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(genreDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("возвращать статус 404 при несуществующем id жанре")
    @Test
    void shouldNotUpdateGenreWIthNotValidGenreId() throws Exception {
        long genreId = 100L;
        GenreDto genreUpdatedDto = new GenreDto(genreId, UPDATED_GENRE_NAME);

        when(genreService.update(genreUpdatedDto)).thenThrow(new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));

        ResultActions response = mvc.perform(put("/api/v1/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(genreUpdatedDto))
        );
        response.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("удалять жанр по id")
    void shouldCorrectDeleteGenre() throws Exception {

        willDoNothing().given(genreService).delete(FIRST_GENRE_ID);

        ResultActions response = mvc.perform(delete("/api/v1/genre/{id}", FIRST_GENRE_ID));

        response.andExpect(status().isOk())
                .andDo(print());
    }

    private static GenreDto getFirstGenre() {
        return new GenreDto(FIRST_GENRE_ID, FIRST_GENRE_NAME);
    }

}