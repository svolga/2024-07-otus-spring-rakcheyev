package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Comment должен ")
@DataMongoTest
public class CommentRepositoryTest {

    private static final String FIRST_BOOK_ID = "1";
    private static final String SECOND_BOOK_ID = "2";

    private static final String FIRST_COMMENT_ID = "1";
    private static final String CREATED_COMMENT_ID = "100";

    private static final String CREATED_COMMENT = "New comment";
    private static final String UPDATED_COMMENT = "Updated comment";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    Book book1;
    Book book2;

    @BeforeEach
    void setUp() {
        book1 = getBookById(FIRST_BOOK_ID);
        book2 = getBookById(SECOND_BOOK_ID);
    }

    @Test
    @DisplayName("возвращать список комментариев для книги")
    void shouldFindAllCommentsByBook() {
        var actualComments = commentRepository.findAllByBookId(FIRST_BOOK_ID);
        var expectedComments = mongoTemplate.findAll(Comment.class).stream()
                .filter(comment -> Objects.equals(comment.getBook().getId(), FIRST_BOOK_ID)).toList();

        assertThat(actualComments.size()).isEqualTo(expectedComments.size());
        assertThat(actualComments).hasOnlyElementsOfType(Comment.class);
        actualComments.forEach(System.out::println);
    }

    @Test
    @DisplayName("создавать новый комментарий")
    void shouldCreateComment() {
        var expectedComment = new Comment(CREATED_COMMENT_ID, CREATED_COMMENT, book1);
        var returnedComment = commentRepository.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> !comment.getId().isBlank())
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(commentRepository.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(returnedComment);
    }

    @Test
    @DisplayName("обновлять комментарий к книге")
    void shouldUpdateComment() {

        var expectedComment = new Comment(FIRST_COMMENT_ID, UPDATED_COMMENT, book1);
        System.out.println(expectedComment);
        assertThat(commentRepository.findById(expectedComment.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedComment);

        var returnedBook = commentRepository.save(expectedComment);
        assertThat(returnedBook).isNotNull()
                .matches(book -> !book.getId().isBlank())
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);
    }

    @DisplayName("удалять комментарий")
    @Test
    void shouldDeleteComment() {
        assertThat(commentRepository.findById(FIRST_COMMENT_ID)).isNotEmpty();
        commentRepository.deleteById(FIRST_COMMENT_ID);
        assertThat(commentRepository.findById(FIRST_COMMENT_ID)).isEmpty();
    }

    private Book getBookById(String id) {
        return bookRepository.findById(id).get();
    }

}