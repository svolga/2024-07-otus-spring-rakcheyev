package ru.otus.hw.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис Comment должен ")
@DataMongoTest
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({CommentServiceImpl.class})
public class CommentServiceImplTest {

    private static final int EXPECTED_COMMENTS_FOR_BOOK1_COUNT = 2;
    private static final String FIRST_BOOK_ID = "1";

    private static final String FIRST_COMMENT_ID = "1";
    private static final String FIRST_BOOK_FIRST_COMMENT_TEXT = "Book1_Comment_1";

    private static final String CREATED_COMMENT = "New comment";
    private static final String UPDATED_COMMENT = "Updated comment";

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("возвращать комментарий по id")
    public void shouldFindAllCommentById() {
        var actualComment = commentService.findById(FIRST_COMMENT_ID);
        assertThat(actualComment).isPresent();
        assertThat(actualComment.get().getText()).isEqualTo(FIRST_BOOK_FIRST_COMMENT_TEXT);
    }

    @DisplayName("возвращать список комментариев для книги")
    @Test
    void shouldFindAllCommentsByBook() {
        var actualComments = commentService.findAllByBookId(FIRST_BOOK_ID);

        assertThat(actualComments).isNotEmpty()
                .hasSize(EXPECTED_COMMENTS_FOR_BOOK1_COUNT)
                .hasOnlyElementsOfType(Comment.class);
    }

    @Test
    @DisplayName("обновлять комментарий")
    void shouldUpdateComment() {
        var actualComment = commentService.findById(FIRST_COMMENT_ID);
        var expectedComment = commentService.update(actualComment.get().getId(), UPDATED_COMMENT, FIRST_COMMENT_ID);
        var updatedBook = commentService.findById(FIRST_COMMENT_ID);
        assertThat(expectedComment).isNotNull();
        assertThat(expectedComment.getId()).isEqualTo(updatedBook.get().getId());
    }

    @Test
    @DisplayName("сохранять комментарий")
    void shouldSaveComment() {
        var expectedComment = commentService.insert(CREATED_COMMENT, FIRST_BOOK_ID);
        var returnedComment = commentService.findById(expectedComment.getId());

        assertThat(returnedComment).isNotNull();
        assertThat(expectedComment.getId()).isEqualTo(returnedComment.get().getId());
    }

}