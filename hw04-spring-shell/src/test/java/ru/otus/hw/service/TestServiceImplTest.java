package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Тест TestServiceImpl")
public class TestServiceImplTest {

    @MockBean
    private LocalizedIOService ioService;

    @MockBean
    private CsvQuestionDao csvQuestionDao;

    @Autowired
    private TestService testService;

    @DisplayName("выполнить executeTestFor")
    @Test
    public void shouldExecuteTestWithRightAnswerCount() {

        Student student = new Student("FirstName", "LastName");

        List<Question> list = List.of(
                new Question("First question", List.of(
                        new Answer("Answer 11", true)
                )),
                new Question("Second question", List.of(
                        new Answer("Answer 21", false)
                ))
        );

        when(csvQuestionDao.findAll())
                .thenReturn(list);

        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(),anyString()))
                .thenReturn(1)
                .thenReturn(1);

        TestResult testResult = testService.executeTestFor(student);
        assertEquals(1, testResult.getRightAnswersCount());
    }
}
