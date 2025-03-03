package ru.otus.hw.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"user"})
@ToString
@Table(name = "teachers_profiles")
public class UserTeacherProfile {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "info")
    private String info;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
