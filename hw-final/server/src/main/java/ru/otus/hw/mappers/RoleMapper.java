package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.hw.dto.RoleDto;
import ru.otus.hw.models.Role;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "role", target = "name")
    RoleDto toDto(Role role);

    @Mapping(source = "name", target = "role")
    Role toEntity(RoleDto roleDto);

    List<RoleDto> toDtos(List<Role> roles);
}
