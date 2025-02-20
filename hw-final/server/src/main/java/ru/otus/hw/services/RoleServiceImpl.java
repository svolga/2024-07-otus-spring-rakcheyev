package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.RoleDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.RoleMapper;
import ru.otus.hw.repositories.RoleRepository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> findAll() {
        var genres = roleRepository.findAll();
        return roleMapper.toDtos(genres);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto findById(String id) {
        var role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role with id %s not found".formatted(id)));
        return roleMapper.toDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> findAllByIds(Set<String> ids) {
        var roles = roleRepository.findAllByRoleIn(ids);
        return roleMapper.toDtos(roles);
    }

}
