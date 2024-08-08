package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CsvQuestionDaoTest")
public class CsvQuestionDaoTest {

    ApplicationContext context;
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    public void setUp() {
        context = new ClassPathXmlApplicationContext("/spring-context.xml");
        csvQuestionDao = context.getBean(CsvQuestionDao.class);
    }

    @Test
    void shouldReturnQuestions() {
        List<Question> list = csvQuestionDao.findAll();
        assertEquals(3, list.size());
    }
}