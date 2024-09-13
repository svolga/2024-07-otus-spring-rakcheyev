package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestService;

@ShellComponent("Application testing for students")
@RequiredArgsConstructor
public class ShellCommands {

    private TestResult testResult;

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private Student student;

    @ShellMethod(value = "Create account", key = {"l", "login"})
    public void login() {
        student = studentService.determineCurrentStudent();
        testResult = null;
    }

    @ShellMethod(value = "Run tests", key = {"t", "test"})
    @ShellMethodAvailability(value = "isStudentAvailable")
    public void test() {
        testResult = testService.executeTestFor(student);
    }

    @ShellMethod(value = "Tests results", key = {"r", "results"})
    @ShellMethodAvailability(value = "isResultAvailable")
    public void results() {
        resultService.showResult(testResult);
    }

    private Availability isStudentAvailable() {
        return student == null ?
                Availability.unavailable("Login your account") :
                Availability.available();
    }

    private Availability isResultAvailable() {
        return testResult == null ?
                Availability.unavailable("You should run test") :
                Availability.available();
    }
}