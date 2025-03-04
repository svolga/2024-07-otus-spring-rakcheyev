package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        return em.createQuery("select b from Book b where b.id = :id", Book.class)
                .setParameter("id", id)
                .setHint(FETCH.getKey(), em.getEntityGraph("otus-student-author-genres-entity-graph"))
                .getResultList().stream().findFirst();
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-student-author-entity-graph");
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b ", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);

        List<Book> books = query.getResultList();
        if (!books.isEmpty()) {
            books.get(0).getGenres().size();
        }
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        var book = findById(id).orElseThrow(() ->
                new EntityNotFoundException("Book with id %d not found".formatted(id)));
        em.remove(book);
    }
}
