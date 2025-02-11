package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, String> {
    List<Role> findAllByRoleIn(Set<String> ids);
}