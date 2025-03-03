package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Homework;

import java.util.List;
import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    @Override
    @EntityGraph("homework-task-entity-graph")
    Optional<Homework> findById(Long id);

    @Override
    @EntityGraph("homework-task-entity-graph")
    List<Homework> findAll();
}