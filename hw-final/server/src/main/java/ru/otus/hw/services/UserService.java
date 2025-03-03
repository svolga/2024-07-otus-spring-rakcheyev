package ru.otus.hw.services;

import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserInfoDto;
import ru.otus.hw.dto.UserTeacherProfileDto;

import java.util.List;

public interface UserService {
    UserDto findById(long id);

    List<UserInfoDto> findAll();

    List<UserInfoDto> findAllStudents();

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void deleteById(long id);

    List<UserTeacherProfileDto> findAllTeachers();

    UserTeacherProfileDto findTeacherById(long id);

    UserTeacherProfileDto updateTeacherProfile(UserTeacherProfileDto teacherProfileDto);


}
