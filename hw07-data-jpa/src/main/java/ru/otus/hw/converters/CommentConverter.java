package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

@Component
@RequiredArgsConstructor
public class CommentConverter {
    public String commentToString(Comment comment) {
        return "Id: %d, content: %s".formatted(
                comment.getId(),
                comment.getText());
    }
}
