package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CourseDto;
import ru.otus.hw.mappers.AuthorMapperImpl;
import ru.otus.hw.mappers.CourseMapperImpl;
import ru.otus.hw.mappers.GenreMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Сервис Course должен ")
@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({CourseServiceImpl.class, CourseMapperImpl.class, AuthorMapperImpl.class, GenreMapperImpl.class})
public class CourseServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_COURSES = 5;

    private static final long FIRST_COURSE_ID = 1L;
    private static final String FIRST_COURSE_TITLE = "CourseTitle_1";
    private static final String FIRST_COURSE_INFO = "CourseDesc_1";
    private static final int FIRST_COURSE_PRICE = 120_000;

    private static final String COURSE_TITLE_UPDATED = "CourseTitle_Updated";

    @Autowired
    private CourseService courseService;

    @Test
    @DisplayName("загружать курс по id")
    public void shouldFindCourseById() {
        var actualCourse = courseService.findById(FIRST_COURSE_ID);
        var expectedCourse = getFirstCourse();

        assertThat(actualCourse).isNotNull()
                .matches(course -> course.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedCourse);
    }

    @DisplayName("загружать список всех курсов")
    @Test
    void shouldReturnCorrectCoursesList() {
        var actualCourses = courseService.findAll();

        assertThat(actualCourses).isNotEmpty()
                .hasSize(EXPECTED_NUMBER_OF_COURSES)
                .hasOnlyElementsOfType(CourseDto.class);

        assertThat(actualCourses.get(0).getName()).isEqualTo(FIRST_COURSE_TITLE);
    }

    @DisplayName("сохранять новый курс")
    @Test
    void shouldSaveCreatedCourse() {
        var expectedCourse = getNewCourseData();
        var actualCourse = courseService.create(expectedCourse);
        expectedCourse.setId(6L);
        assertThat(actualCourse).isNotNull()
                .matches(course -> course.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedCourse);
    }


    @DisplayName("сохранять измененный курс")
    @Test
    void shouldSaveUpdatedCourse() {
        var expectedCourse = getFirstCourse();
        expectedCourse.setName(COURSE_TITLE_UPDATED);

        assertThat(courseService.findById(expectedCourse.getId()))
                .isNotNull();

        assertThat(courseService.findById(expectedCourse.getId()).getName()).isNotEqualTo(expectedCourse.getName());

        var actualCourse = courseService.update(expectedCourse);
        assertThat(actualCourse).isNotNull()
                .matches(course -> course.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedCourse);

        assertThat(courseService.findById(actualCourse.getId()))
                .isNotNull();

    }

    @DisplayName("удалять курс по id ")
    @Test
    void shouldDeleteCourse() {

        assertThat(courseService.findById(FIRST_COURSE_ID)).isNotNull();
        courseService.delete(FIRST_COURSE_ID);

        assertThatThrownBy(() -> courseService.findById(FIRST_COURSE_ID)).isInstanceOf(Exception.class)
                .hasMessage("Course with id 1 not found");
    }

    private static CourseDto getFirstCourse() {
        return CourseDto.builder()
                .id(FIRST_COURSE_ID)
                .name(FIRST_COURSE_TITLE)
                .info(FIRST_COURSE_INFO)
                .price(FIRST_COURSE_PRICE)
                .build();
    }

    private static CourseDto getNewCourseData() {
        return CourseDto.builder()
                .id(0L)
                .name(FIRST_COURSE_TITLE)
                .info(FIRST_COURSE_INFO)
                .price(FIRST_COURSE_PRICE)
                .build();
    }

}