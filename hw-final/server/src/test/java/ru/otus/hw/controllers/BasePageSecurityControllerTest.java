package ru.otus.hw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BasePageSecurityControllerTest {

    @Autowired
    private MockMvc mvc;

    protected static final String LOGIN_REDIRECT_PATTERN = "**/login";

    protected MockHttpServletRequestBuilder method2RequestBuilder(String method, String url) {
        Map<String, Function<String, MockHttpServletRequestBuilder>> methodMap =
                Map.of(
                        "get", MockMvcRequestBuilders::get,
                        "post", MockMvcRequestBuilders::post
                );

        return methodMap.get(method).apply(url);
    }

    protected void getExpectedStatus(String method, String url,
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

}