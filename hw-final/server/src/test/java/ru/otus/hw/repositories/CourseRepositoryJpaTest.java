package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Course;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Course должен ")
@DataJpaTest
public class CourseRepositoryJpaTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CourseRepository courseRepository;

    private static final long FIRST_COURSE_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_COURSES = 5;
    private static final String COURSE_TITLE_CREATED = "CourseName_Created";
    private static final String COURSE_TITLE_UPDATED = "CourseName_Updated";

    @DisplayName("загружать курс по id")
    @Test
    void shouldFindCourseById() {
        val optionalActualCourse = courseRepository.findById(FIRST_COURSE_ID);
        val expectedCourse = em.find(Course.class, FIRST_COURSE_ID);
        assertThat(optionalActualCourse).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedCourse);
    }

    @DisplayName("загружать список всех курсов")
    @Test
    void shouldFindAllCourses() {
        var courses = courseRepository.findAll();
        var expectedCourse1 = em.find(Course.class, FIRST_COURSE_ID);

        assertThat(courses).hasSize(EXPECTED_NUMBER_OF_COURSES);
        assertThat(expectedCourse1.getId()).isEqualTo(courses.get(0).getId());
    }

    @DisplayName("сохранять новый курс")
    @Test
    void shouldSaveNewCourse() {
        var expectedCourse = getCreatedCourse();
        var savedCourse = courseRepository.save(expectedCourse);
        assertThat(savedCourse).isNotNull()
                .matches(course -> course.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedCourse);

        assertThat(em.find(Course.class,
                savedCourse.getId())).isEqualTo(savedCourse);
    }

    @DisplayName("сохранять измененный курс")
    @Test
    void shouldSaveUpdatedCourse() {
        var expectedCourse = getUpdatedCourse();
        assertThat(em.find(Course.class, expectedCourse.getId())).isNotEqualTo(expectedCourse);

        var savedCourse = courseRepository.save(expectedCourse);
        assertThat(savedCourse).isNotNull()
                .matches(course -> course.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedCourse);

        assertThat(em.find(Course.class, savedCourse.getId())).isEqualTo(savedCourse);

    }

    @DisplayName("удалять курс по id")
    @Test
    void shouldDeleteCourse() {
        assertThat(em.find(Course.class, FIRST_COURSE_ID)).isNotNull();
        courseRepository.deleteById(FIRST_COURSE_ID);
        assertThat(em.find(Course.class, FIRST_COURSE_ID)).isNull();
    }

    private Course getCreatedCourse() {
        return Course.builder()
                .id(0)
                .name(COURSE_TITLE_CREATED)
                .info("Desc")
                .price(100000)
                .build();
    }

    private Course getUpdatedCourse() {
        return Course.builder()
                .id(FIRST_COURSE_ID)
                .name(COURSE_TITLE_UPDATED)
                .info("Desc")
                .price(100000)
                .build();
    }


}
