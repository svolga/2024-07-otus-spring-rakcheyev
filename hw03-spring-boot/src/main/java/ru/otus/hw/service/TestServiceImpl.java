package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        return askQuestions(questions, student);
    }

    private TestResult askQuestions(List<Question> questions, Student student) {
        var testResult = new TestResult(student);

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            var questionWithAnswers = convertQuestionToString(question, i + 1);
            var answerIndex = ioService.readIntForRangeWithPrompt(
                    1,
                    question.answers().size(),
                    questionWithAnswers,
                    ioService.getMessage("TestService.enter.nums.from") + question.answers().size()
            );

            Answer userAnswer = question.answers().get(answerIndex - 1);
            testResult.applyAnswer(question, userAnswer.isCorrect());
            ioService.printLine("");
        }

        return testResult;
    }

    private String convertQuestionToString(Question question, int questionNumber) {

        String header = String.format("--> %d. %s %s", questionNumber, question.text(), System.lineSeparator());

        return IntStream.range(0, question.answers().size())
                .mapToObj(index -> {
                    Answer answer = question.answers().get(index);
                    return String.format("%d. %s", index + 1, answer.text());
                })
                .collect(Collectors.joining(System.lineSeparator(), header, System.lineSeparator()));
    }
}
