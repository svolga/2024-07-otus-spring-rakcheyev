package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @DisplayName("выполнить executeTest")
    @Test
    public void shouldExecuteTest() {
        List<Question> list = List.of(new Question("First", List.of()));
        when(csvQuestionDao.findAll()).thenReturn(list);
        testService.executeTest();
        verify(csvQuestionDao, times(1)).findAll();
        verify(ioService, atLeast(1)).printLine("");
        verify(ioService, times(1)).printFormattedLine("Please answer the questions below%n");
    }
}