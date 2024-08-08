package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private static final char DELIMETER_QUESTION_TO_ANSWERS = ';';

    private static final int START_LINE = 1;

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        List<QuestionDto> questionDtos;
        String filename = fileNameProvider.getTestFileName();

        try {
            FileReader fileReader = new FileReader(getFileFromResource(filename));
            questionDtos = getQuestionDtos(fileReader);
        } catch (QuestionReadException | FileNotFoundException e) {
            throw new QuestionReadException(e.getMessage());
        }

        return convertQuestionDtoToQuestion(questionDtos);
    }

    private List<Question> convertQuestionDtoToQuestion(List<QuestionDto> questionDtos) {
        return questionDtos.stream()
                .map(QuestionDto::toDomainObject)
                .toList();
    }

    private List<QuestionDto> getQuestionDtos(FileReader fileReader) {
        return new CsvToBeanBuilder<QuestionDto>(fileReader)
                .withProfile("questions")
                .withSeparator(DELIMETER_QUESTION_TO_ANSWERS)
                .withType(QuestionDto.class)
                .withSkipLines(START_LINE)
                .build()
                .parse();
    }

    private File getFileFromResource(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
