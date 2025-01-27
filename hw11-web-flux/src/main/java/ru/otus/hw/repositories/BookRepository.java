package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.Book;

import java.util.List;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Void> deleteAllBooksByAuthorId(String authorId);
    Flux<Book> findAllBooksByGenresIdIn(List<String> genreIds);
}