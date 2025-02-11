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

@DisplayName("Контроллер AuthorPage должен ")
@WebMvcTest(AuthorPageController.class)
class AuthorPageControllerTest {

    @Autowired
    private MockMvc mvc;

    private final static String INDEX_FILE = "views/author/index";
    private final static String EDIT_FILE = "views/author/edit";

    @Test
    @DisplayName("возвращать страницу со списком авторов")
    void shouldReturnCorrectIndexPage() throws Exception {
        mvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(view().name(INDEX_FILE));
    }

    @Test
    @DisplayName("открывать страницу создания автора")
    void shouldReturnCorrectCreateGetPage() throws Exception {
        mvc.perform(get("/author/create"))
                .andExpect(status().isOk())
                .andExpect(view().name(EDIT_FILE));
    }

    @Test
    @DisplayName("редактировать автора")
    void shouldReturnCorrectUpdatePage() throws Exception {

        mvc.perform(get("/author/edit")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("id"))
                .andExpect(view().name(EDIT_FILE));
    }

}