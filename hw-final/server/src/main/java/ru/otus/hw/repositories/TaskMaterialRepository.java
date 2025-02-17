package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.TaskMaterial;

public interface TaskMaterialRepository extends JpaRepository<TaskMaterial, Long> {

}
