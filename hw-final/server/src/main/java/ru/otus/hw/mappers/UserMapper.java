package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserInfoDto;
import ru.otus.hw.models.Role;
import ru.otus.hw.models.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "middleName", target = "middleName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roleDtos", qualifiedByName = "getRoleSet")
    UserDto toDto(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = ".", target = "fullName", qualifiedByName = "getFullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roleTitles", qualifiedByName = "getRolesTitles")
    UserInfoDto toUserInfoDto(User user);

    List<UserDto> toDtos(List<User> users);
    List<UserInfoDto> toUserInfoDtos(List<User> users);

    @Mapping(target = "roles", ignore = true)
    default User toEntity(UserDto userDto, Set<Role> roles) {

        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .middleName(userDto.getMiddleName())
                .lastName(userDto.getLastName())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .roles(roles).build();
    }

    @Named("getRolesTitles")
    default String getRoleTitles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getRole)
                .collect(Collectors.joining(", "));

    }

    @Named("getRoleSet")
    default Set<String> getRoleSet(Set<Role> roles) {
        return roles.stream().map(Role::getRole).collect(Collectors.toSet());
    }

    @Named("getFullName")
    default String getFullName(User user) {
        return String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getMiddleName());
    }
}
