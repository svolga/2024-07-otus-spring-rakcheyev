package ru.otus.hw.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"student", "homework", "rank"})
@Entity
@Builder
@Table(name = "results")
@NamedEntityGraph(name = "result-student-homework-rank-entity-graph",
        attributeNodes = {
            @NamedAttributeNode("student"),
            @NamedAttributeNode("homework"),
            @NamedAttributeNode("rank")
        })
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "step")
    private int step;

    @Column(name = "score")
    private Integer score;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User student;

    @JoinColumn(name = "homework_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Homework homework;

    @JoinColumn(name = "rank_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Rank rank;

}
