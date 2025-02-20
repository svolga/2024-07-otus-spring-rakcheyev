package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.hw.dto.ResultReport;
import ru.otus.hw.models.Result;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {

    @Override
    @EntityGraph("result-student-homework-rank-entity-graph")
    Optional<Result> findById(Long id);

    @Override
    @EntityGraph("result-student-homework-rank-entity-graph")
    List<Result> findAll();

    @Query(value = "" +
            "select r.user_id as userId, concat(u.last_name, ' ', u.first_name, ' ', u.middle_name)  as fullName, " +
            "sum(r.score) as score " +
            "from results r " +
            "left join users u on r.user_id = u.id " +
            "group by r.user_id " +
            "order by score desc",
            nativeQuery = true)
    List<ResultReport> getBestResults();

    @Query(value = "" +
            "select r.user_id as userId, concat(u.last_name, ' ', u.first_name, ' ', u.middle_name)  as fullName, " +
            "avg(r.score) as score " +
            "from results r " +
            "left join users u on r.user_id = u.id " +
            "group by r.user_id " +
            "order by score desc",
            nativeQuery = true)
    List<ResultReport> getMiddleScoreResults();

}