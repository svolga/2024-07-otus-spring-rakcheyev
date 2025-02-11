package ru.otus.hw.services;

import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserInfoDto;

import java.util.List;

public interface UserService {
    UserDto findById(long id);

    List<UserInfoDto> findAll();

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void deleteById(long id);
}
