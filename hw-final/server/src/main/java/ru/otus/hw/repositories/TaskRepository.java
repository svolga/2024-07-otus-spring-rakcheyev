package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Override
    @EntityGraph("task-teacher-group-materials-entity-graph")
    Optional<Task> findById(Long id);

    @Override
    @EntityGraph("task-teacher-group-materials-entity-graph")
    List<Task> findAll();
}