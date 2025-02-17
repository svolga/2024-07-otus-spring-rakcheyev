package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Rank;

public interface RankRepository extends JpaRepository<Rank, Long> {
}