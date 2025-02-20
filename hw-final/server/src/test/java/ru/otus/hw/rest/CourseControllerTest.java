package ru.otus.hw.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.otus.hw.dto.CourseDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.CourseService;

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

@DisplayName("Контроллер Course должен ")
@WebMvcTest(controllers = CourseController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private static final long FIRST_COURSE_ID = 1L;
    private static final String FIRST_COURSE_TITLE = "CourseTitle_1";
    private static final String FIRST_COURSE_INFO = "CourseDesc_1";
    private static final int FIRST_COURSE_PRICE = 120_000;

    private static final long SECOND_COURSE_ID = 2L;
    private static final String SECOND_COURSE_NAME = "Course_2";

    private static final String UPDATED_COURSE_NAME = "Updated Course";

    @Test
    @DisplayName("возвращать список курсов")
    void shouldReturnCorrectCourseList() throws Exception {
        var courseDtos = List.of(
                getFirstCourse()
        );

        when(courseService.findAll()).thenReturn(courseDtos);

        mvc.perform(get("/api/v1/course"))
                .andExpect(content().json(mapper.writeValueAsString(courseDtos)))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("возвращать курс по id")
    void shouldReturnCorrectCourseById() throws Exception {
        var courseDto = getFirstCourse();
        given(courseService.findById(FIRST_COURSE_ID)).willReturn(courseDto);

        mvc.perform(get("/api/v1/course/{id}", FIRST_COURSE_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(courseDto)))
                .andDo(print());
    }

    @DisplayName("создавать курс")
    @Test
    void shouldCreateAuthor() throws Exception {
        CourseDto courseDto = getFirstCourse();
        CourseDto expectedCourseDto = CourseDto.builder()
                .id(courseDto.getId())
                .name(courseDto.getName())
                .price(courseDto.getPrice())
                .info(courseDto.getInfo())
                .build();

        when(courseService.create(courseDto)).thenReturn(expectedCourseDto);

        ResultActions response = mvc.perform(post("/api/v1/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedCourseDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("обновлять курс")
    @Test
    void shouldUpdateAuthor() throws Exception {
        CourseDto courseDto = getFirstCourse();
        CourseDto courseUpdatedDto = CourseDto.builder()
                .id(courseDto.getId())
                .name(UPDATED_COURSE_NAME)
                .price(courseDto.getPrice())
                .info(courseDto.getInfo())
                .build();

        when(courseService.update(courseUpdatedDto)).thenReturn(courseDto);

        ResultActions response = mvc.perform(put("/api/v1/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(courseDto))
        );
        response.andExpect(status().isOk());
    }

    @DisplayName("возвращать статус 404 при несуществующем id курса")
    @Test
    void shouldNotUpdateCourseWIthNotValidCourseId() throws Exception {
        long courseId = 100L;
        CourseDto courseDto = getFirstCourse();
        CourseDto courseUpdatedDto = CourseDto.builder()
                .id(courseId)
                .name(UPDATED_COURSE_NAME)
                .price(courseDto.getPrice())
                .info(courseDto.getInfo())
                .build();

        when(courseService.update(courseUpdatedDto)).thenThrow(new EntityNotFoundException("Course with id %d not found".formatted(courseId)));

        ResultActions response = mvc.perform(put("/api/v1/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(courseUpdatedDto))
        );
        response.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("удалять курс по id")
    void shouldCorrectDeleteCourse() throws Exception {

        willDoNothing().given(courseService).delete(FIRST_COURSE_ID);

        ResultActions response = mvc.perform(delete("/api/v1/course/{id}", FIRST_COURSE_ID));

        response.andExpect(status().isOk())
                .andDo(print());
    }

    private static CourseDto getFirstCourse() {
        return CourseDto.builder()
                .id(FIRST_COURSE_ID)
                .name(FIRST_COURSE_TITLE)
                .info(FIRST_COURSE_INFO)
                .price(FIRST_COURSE_PRICE)
                .build();
    }
}