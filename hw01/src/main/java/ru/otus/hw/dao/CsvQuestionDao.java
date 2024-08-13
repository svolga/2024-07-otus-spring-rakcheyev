package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private static final char DELIMETER_QUESTION_TO_ANSWERS = ';';

    private static final int START_LINE = 1;

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {

        List<QuestionDto> questionDtos;
        String filename = fileNameProvider.getTestFileName();

        try (InputStream inputStream = getFileFromResourceAsStream(filename);
             InputStreamReader streamReader =
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            questionDtos = getQuestionDtos(reader);
        } catch (IllegalArgumentException | IOException e) {
            throw new QuestionReadException(e.getMessage(), e);
        }

        return convertQuestionDtoToQuestion(questionDtos);
    }

    private List<Question> convertQuestionDtoToQuestion(List<QuestionDto> questionDtos) {
        return questionDtos.stream()
                .map(QuestionDto::toDomainObject)
                .toList();
    }

    private List<QuestionDto> getQuestionDtos(Reader fileReader) {
        return new CsvToBeanBuilder<QuestionDto>(fileReader)
                .withProfile("questions")
                .withSeparator(DELIMETER_QUESTION_TO_ANSWERS)
                .withType(QuestionDto.class)
                .withSkipLines(START_LINE)
                .build()
                .parse();
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

}
