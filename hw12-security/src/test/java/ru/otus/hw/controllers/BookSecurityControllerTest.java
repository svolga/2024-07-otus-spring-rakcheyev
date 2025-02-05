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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.security.CustomUserDetailService;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер Book должен ")
@Import(SecurityConfiguration.class)
@WebMvcTest(controllers = BookController.class)
class BookSecurityControllerTest {

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookService bookService;

    @MockBean
    CustomUserDetailService customUserDetailService;

    @Autowired
    private MockMvc mvc;

    private static final String LOGIN_REDIRECT_PATTERN = "**/login";

    protected MockHttpServletRequestBuilder method2RequestBuilder(String method, String url) {
        Map<String, Function<String, MockHttpServletRequestBuilder>> methodMap =
                Map.of(
                        "get", MockMvcRequestBuilders::get,
                        "post", MockMvcRequestBuilders::post
                );

        return methodMap.get(method).apply(url);
    }

    @DisplayName("возвращать ожидаемый статус")
    @ParameterizedTest(name = "{0} {1} для пользователя {2} должен вернуть статус {4} ")
    @MethodSource("getTestData")
    void shouldReturnExpectedStatus(String method, String url,
                                    String username, String[] roles, int status,
                                    String redirectPattern
    ) throws Exception {
        var request = method2RequestBuilder(method, url);

        if (nonNull(username)) {
            request = request
                    .with(user(username).roles(roles));
        }
        ResultActions resultActions = mvc.perform(request)
                .andExpect(status().is(status));

        if (nonNull(redirectPattern)) {
            resultActions.andExpect(redirectedUrlPattern(redirectPattern));
        }
    }

    public static Stream<Arguments> getTestData() {
        var roles = new String[]{"USER", "ADMIN"};
        return Stream.of(
                Arguments.of("get", "/", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("get", "/", "user", roles, 200, null),
                Arguments.of("get", "/book/create", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("get", "/book/create", "user", roles, 200, null),
                Arguments.of("post", "/book/edit", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("post", "/book/edit", "user", roles, 200, null),
                Arguments.of("post", "/book/delete", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("post", "/book/delete?id=1", "user", roles, 302, "/**")
        );

    }

}