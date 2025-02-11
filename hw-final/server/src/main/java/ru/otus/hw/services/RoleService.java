package ru.otus.hw.services;

import ru.otus.hw.dto.RoleDto;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<RoleDto> findAll();
    RoleDto findById(String id);
    List<RoleDto> findAllByIds(Set<String> ids);
}
