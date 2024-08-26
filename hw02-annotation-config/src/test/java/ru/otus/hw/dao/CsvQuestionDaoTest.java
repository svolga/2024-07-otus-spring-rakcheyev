package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Dao CsvQuestionDaoTest ")
public class CsvQuestionDaoTest {

    private TestFileNameProvider fileNameProvider;
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    public void setUp() {
        fileNameProvider = mock(TestFileNameProvider.class);
        csvQuestionDao = new CsvQuestionDao(fileNameProvider);
    }

    @Test
    @DisplayName("верный формат файла")
    public void shouldReadRightFile() {
        when(fileNameProvider.getTestFileName()).thenReturn("questions_ok.csv");
        List<Question> questions = csvQuestionDao.findAll();
        assertFalse(questions.isEmpty());
    }

    @Test
    @DisplayName("неверный формат файла")
    public void shouldThrowException() {
        when(fileNameProvider.getTestFileName()).thenReturn("questions_failed.csv");
        assertThrows(QuestionReadException.class, csvQuestionDao::findAll);
    }

    @Test
    @DisplayName("файл не существует")
    public void shouldThrowQuestionReadExceptionForFileNotFound() {
        when(fileNameProvider.getTestFileName()).thenReturn("unknowwn.csv");
        assertThrows(QuestionReadException.class, csvQuestionDao::findAll);
    }

}