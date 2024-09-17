package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find author", key = "ao")
    public String findAuthor(long id) {
        Optional<Author> author = authorService.findById(id);
        if (author.isPresent()) {
            return authorConverter.authorToString(author.get());
        }
        return "not found";
    }

    @ShellMethod(value = "Open h2 terminal", key = "term")
    public void terminal() throws SQLException {
        Console.main(null);
    }

}
