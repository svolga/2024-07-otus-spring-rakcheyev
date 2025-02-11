package ru.otus.hw.pages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Контроллер GenrePage должен ")
@WebMvcTest(GenrePageController.class)
class GenrePageControllerTest {

    @Autowired
    private MockMvc mvc;

    private final static String INDEX_FILE = "views/genre/index";
    private final static String EDIT_FILE = "views/genre/edit";

    @Test
    @DisplayName("возвращать страницу со списком жанров")
    void shouldReturnCorrectIndexPage() throws Exception {
        mvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(view().name(INDEX_FILE));
    }

    @Test
    @DisplayName("открывать страницу создания жанра")
    void shouldReturnCorrectCreateGetPage() throws Exception {
        mvc.perform(get("/genre/create"))
                .andExpect(status().isOk())
                .andExpect(view().name(EDIT_FILE));
    }

    @Test
    @DisplayName("редактировать жанра")
    void shouldReturnCorrectUpdatePage() throws Exception {

        mvc.perform(get("/genre/edit")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("id"))
                .andExpect(view().name(EDIT_FILE));
    }

}