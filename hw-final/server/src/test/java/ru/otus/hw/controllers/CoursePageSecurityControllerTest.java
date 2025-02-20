package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.pages.CoursePageController;
import ru.otus.hw.repositories.CourseRepository;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.security.CustomUserDetailService;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.CourseService;

import java.util.stream.Stream;

@DisplayName("Контроллер CoursePage security должен ")
@Import(SecurityConfiguration.class)
@WebMvcTest(controllers = CoursePageController.class)
class CoursePageSecurityControllerTest extends BasePageSecurityControllerTest {

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    CustomUserDetailService customUserDetailService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @DisplayName("возвращать ожидаемый статус")
    @ParameterizedTest(name = "{0} {1} для пользователя {2} должен вернуть статус {4} ")
    @MethodSource("getTestData")
    void shouldReturnExpectedStatus(String method, String url,
                                    String username, String[] roles, int status,
                                    String redirectPattern
    ) throws Exception {
        getExpectedStatus(method, url, username, roles, status, redirectPattern);
    }

    public static Stream<Arguments> getTestData() {
        var roles = new String[]{"USER", "ADMIN"};
        return Stream.of(
                Arguments.of("get", "/course", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("get", "/course", "admin", roles, 200, null),
                Arguments.of("get", "/course/create", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("get", "/course/create", "admin", roles, 200, null),
                Arguments.of("get", "/course/edit", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("get", "/course/edit?id=1", "admin", roles, 200, null)
        );

    }

}