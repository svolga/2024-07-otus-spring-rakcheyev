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
import ru.otus.hw.pages.GroupPageController;
import ru.otus.hw.repositories.GroupRepository;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.security.CustomUserDetailService;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.GroupService;

import java.util.stream.Stream;

@DisplayName("Контроллер GroupPage security должен ")
@Import(SecurityConfiguration.class)
@WebMvcTest(controllers = GroupPageController.class)
class GroupPageSecurityControllerTest extends BasePageSecurityControllerTest {

    @MockBean
    private GroupService groupService;

    @MockBean
    private GroupRepository groupRepository;

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
                Arguments.of("get", "/group", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("get", "/group", "admin", roles, 200, null),
                Arguments.of("get", "/group/create", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("get", "/group/create", "admin", roles, 200, null),
                Arguments.of("get", "/group/edit", null, null, 302, LOGIN_REDIRECT_PATTERN),
                Arguments.of("get", "/group/edit?id=1", "admin", roles, 200, null)
        );

    }

}