package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.UserTeacherProfile;


public interface TeacherProfileRepository extends JpaRepository<UserTeacherProfile, Long> {
}
