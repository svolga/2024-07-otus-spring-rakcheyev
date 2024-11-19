package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Comment должен ")
@DataJpaTest
@Import(BookRepository.class)
public class CommentRepositoryJpaTest {

    private static final int COMMENTS_FOR_BOOK1_COUNT = 1;
    private static final int COMMENTS_FOR_BOOK2_COUNT = 2;

    private static final long FIRST_BOOK_ID = 1L;
    private static final long SECOND_BOOK_ID = 2L;

    private static final long FIRST_COMMENT_ID = 1L;
    private static final long SECOND_COMMENT_ID = 2L;
    private static final long THIRD_COMMENT_ID = 3L;

    private static final String CREATED_COMMENT = "New comment";
    private static final String UPDATED_COMMENT = "Updated comment";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    private List<Comment> commentsBook1;
    private List<Comment> commentsBook2;

    private Comment createdCommentBook1;
    private Comment createdCommentBook2;

    private Comment updatedCommentBook1;
    private Comment updatedCommentBook2;

    @BeforeEach
    void setUp() {
        Book book1 = getBookById(FIRST_BOOK_ID);
        Book book2 = getBookById(SECOND_BOOK_ID);
        commentsBook1 = getCommentsBook1();
        commentsBook2 = getCommentsBook2();

        createdCommentBook1 = getCreatedComment(book1);
        createdCommentBook2 = getCreatedComment(book2);

        updatedCommentBook1 = getUpdatedComment(createdCommentBook1);
        updatedCommentBook2 = getUpdatedComment(createdCommentBook2);
    }

    @Test
    @DisplayName("возвращать список комментариев для книги")
    void shouldFindAllCommentsByBook() {

        var actualCommentsForBook1 = commentRepository.findAllByBookId(FIRST_BOOK_ID);
        assertThat(actualCommentsForBook1)
                .hasSize(COMMENTS_FOR_BOOK1_COUNT)
                .usingRecursiveComparison()
                .isEqualTo(commentsBook1);

        var actualCommentsForBook2 = commentRepository.findAllByBookId(SECOND_BOOK_ID);
        assertThat(actualCommentsForBook2)
                .hasSize(COMMENTS_FOR_BOOK2_COUNT)
                .usingRecursiveComparison()
                .isEqualTo(commentsBook2);
    }

    @Test
    @DisplayName("создавать новый комментарий")
    void shouldCreateComment() {
        List<Comment> createdComments = List.of(createdCommentBook1, createdCommentBook2);
        for (Comment expectedComment : createdComments) {
            var returnedComment = commentRepository.save(expectedComment);
            assertThat(returnedComment).isNotNull()
                    .matches(comment -> comment.getId() > 0)
                    .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

            assertThat(em.find(Comment.class, returnedComment.getId())).isEqualTo(returnedComment);
        }
    }

    @DisplayName("обновлять комментарий к книге")
    @Test
    void shouldUpdatedComment() {
        List<Comment> updatedComments = List.of(updatedCommentBook1, updatedCommentBook2);
        for (Comment expectedComment : updatedComments) {
            assertThat(em.find(Comment.class, expectedComment.getId())).isNotEqualTo(expectedComment);

            var returnedComment = commentRepository.save(expectedComment);
            assertThat(returnedComment).isNotNull()
                    .matches(comment -> comment.getId() > 0)
                    .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

            assertThat(em.find(Comment.class, returnedComment.getId())).isEqualTo(returnedComment);
        }
    }

    @DisplayName("удалять комментарий")
    @Test
    void shouldDeleteComment() {
        assertThat(commentRepository.findById(FIRST_COMMENT_ID)).isNotEmpty();
        commentRepository.deleteById(FIRST_COMMENT_ID);
        assertThat(commentRepository.findById(FIRST_COMMENT_ID)).isEmpty();
    }

    private List<Comment> getCommentsBook1() {
        return List.of(
                getCommentById(FIRST_COMMENT_ID)
        );
    }

    private List<Comment> getCommentsBook2() {
        return List.of(
                getCommentById(SECOND_COMMENT_ID),
                getCommentById(THIRD_COMMENT_ID)
        );
    }

    private Comment getCreatedComment(Book book) {
        return new Comment(0, CREATED_COMMENT, book);
    }

    private Comment getUpdatedComment(Comment comment) {
        return new Comment(comment.getId(), UPDATED_COMMENT, comment.getBook());
    }

    private Book getBookById(long id) {
        return bookRepository.findById(id).get();
    }

    private Comment getCommentById(long id) {
        return commentRepository.findById(id).get();
    }
}