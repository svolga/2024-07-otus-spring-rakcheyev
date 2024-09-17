package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final GenreRepository genreRepository;
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(jdbc.query(
                "select b.id, b.title, a.id as author_id, a.full_name as author_full_name, " +
                        "g.id as genre_id, g.name as genre_name " +
                        "from books b " +
                        "inner join authors a on b.author_id = a.id " +
                        "left join books_genres bg on b.id = bg.book_id " +
                        "left join genres g on bg.genre_id = g.id " +
                        "where b.id = :id", new MapSqlParameterSource("id", id),
                new BookResultSetExtractor()));
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from books where id = :id", params);
    }

    private List<Book> getAllBooksWithoutGenres() {
        return jdbc.query(
                "select b.id, b.title, a.id as author_id, a.full_name as author_full_name " +
                        "from books b " +
                        "inner join authors a on b.author_id = a.id",
                new BookRowMapper()
        );
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbc.query(
                "select book_id, genre_id from books_genres",
                (rs, rowNum) -> new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id")));
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        Map<Long, Genre> genreMap = genres.stream().collect(Collectors.toMap(Genre::getId, Function.identity()));

        for (Book book : booksWithoutGenres) {
            List<Genre> bookGenres = relations.stream()
                    .filter(bookGenreRelation -> bookGenreRelation.bookId() == book.getId())
                    .map(bookGenreRelation -> genreMap.get(bookGenreRelation.genreId()))
                    .collect(Collectors.toList());
            book.setGenres(bookGenres);
        }
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("title", book.getTitle());
        mapSqlParameterSource.addValue("author_id", book.getAuthor().getId());

        jdbc.update("insert into books (title, author_id) values (:title, :author_id)",
                mapSqlParameterSource,
                keyHolder,
                new String[]{"id"}
        );

        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {

        Map<String, Object> params = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "author_id", book.getAuthor().getId());

        int updatedRowCount = jdbc.update(
                "update books set title = :title, author_id = :author_id where id = :id", params);

        if (updatedRowCount == 0) {
            throw new EntityNotFoundException("Not found rows to update");
        }

        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        List<MapSqlParameterSource> params = book.getGenres().stream()
                .map(genre -> new MapSqlParameterSource()
                        .addValue("book_id", book.getId())
                        .addValue("genre_id", genre.getId()))
                .toList();

        jdbc.batchUpdate(
                "insert into books_genres (book_id, genre_id) values (:book_id, :genre_id)",
                params.toArray(new MapSqlParameterSource[0])
        );
    }

    private void removeGenresRelationsFor(Book book) {
        Map<String, Object> params = Collections.singletonMap("bookId", book.getId());
        jdbc.update(
                "delete from books_genres where book_id = :bookId", params);
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author(rs.getLong("author_id"), rs.getString("author_full_name"));
            return new Book(rs.getLong("id"), rs.getString("title"), author, null);
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            Book book = null;
            List<Genre> genres = new ArrayList<>();
            while (rs.next()) {
                if (book == null) {
                    Author author = new Author(rs.getLong("author_id"), rs.getString("author_full_name"));
                    book = new Book(rs.getLong("id"), rs.getString("title"), author, genres);
                }
                long genreId = rs.getLong("genre_id");
                if (genreId > 0) {
                    Genre genre = new Genre(genreId, rs.getString("genre_name"));
                    genres.add(genre);
                }
            }
            return book;
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}
