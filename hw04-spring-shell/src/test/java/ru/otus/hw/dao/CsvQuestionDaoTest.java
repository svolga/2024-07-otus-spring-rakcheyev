package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Тест CsvQuestionDao")
public class CsvQuestionDaoTest {

    @MockBean
    private AppProperties appProperties;

    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @Test
    @DisplayName("верный формат файла")
    public void shouldReadRightFile() {
        when(appProperties.getTestFileName()).thenReturn("questions_ok.csv");
        List<Question> questions = csvQuestionDao.findAll();
        assertFalse(questions.isEmpty());
    }

    @Test
    @DisplayName("неверный формат файла")
    public void shouldThrowException() {
        when(appProperties.getTestFileName()).thenReturn("questions_failed.csv");
        assertThrows(QuestionReadException.class, csvQuestionDao::findAll);
    }

    @Test
    @DisplayName("файл не существует")
    public void shouldThrowQuestionReadExceptionForFileNotFound() {
        when(appProperties.getTestFileName()).thenReturn("unknowwn.csv");
        assertThrows(QuestionReadException.class, csvQuestionDao::findAll);
    }
}
