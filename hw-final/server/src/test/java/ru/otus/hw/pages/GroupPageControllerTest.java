package ru.otus.hw.pages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Контроллер GroupPage должен ")
@WebMvcTest(controllers = GroupPageController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class GroupPageControllerTest {

    @Autowired
    private MockMvc mvc;

    private final static String INDEX_FILE = "views/group/index";
    private final static String EDIT_FILE = "views/group/edit";

    @Test
    @DisplayName("возвращать страницу со списком групп")
    void shouldReturnCorrectIndexPage() throws Exception {
        mvc.perform(get("/group"))
                .andExpect(status().isOk())
                .andExpect(view().name(INDEX_FILE));
    }

    @Test
    @DisplayName("открывать страницу создания группы")
    void shouldReturnCorrectCreateGetPage() throws Exception {
        mvc.perform(get("/group/create"))
                .andExpect(status().isOk())
                .andExpect(view().name(EDIT_FILE));
    }

    @Test
    @DisplayName("редактировать группу")
    void shouldReturnCorrectUpdatePage() throws Exception {

        mvc.perform(get("/group/edit")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("id"))
                .andExpect(view().name(EDIT_FILE));
    }

}