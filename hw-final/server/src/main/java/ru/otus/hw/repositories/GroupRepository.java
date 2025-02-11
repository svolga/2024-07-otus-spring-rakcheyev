package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Group;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByIdIn(Set<Long> ids);

    @Override
    @EntityGraph("group-course-entity-graph")
    Optional<Group> findById(Long id);

    @Override
    @EntityGraph("group-course-entity-graph")
    List<Group> findAll();
}