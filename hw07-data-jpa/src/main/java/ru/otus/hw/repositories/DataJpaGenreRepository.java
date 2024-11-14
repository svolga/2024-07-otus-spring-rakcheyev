package ru.otus.hw.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Genre;

public interface DataJpaGenreRepository extends JpaRepository<Genre, Long> {

/*
   @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public List<Genre> findAllByIds(Set<Long> ids) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.id in (:ids)", Genre.class);
        return query.setParameter("ids",  ids).getResultList();
    }
 */

}
