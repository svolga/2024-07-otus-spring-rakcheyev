package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Сервис TestServiceImpl ")
public class TestServiceImplTest {

    private IOService ioService;
    private CsvQuestionDao csvQuestionDao;
    private TestService testService;

    @BeforeEach
    public void setUp() {
        ioService = mock(IOService.class);
        csvQuestionDao = mock(CsvQuestionDao.class);
        testService = new TestServiceImpl(ioService, csvQuestionDao);
    }

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