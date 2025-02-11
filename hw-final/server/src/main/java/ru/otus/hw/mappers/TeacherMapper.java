package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.otus.hw.dto.TeacherDto;
import ru.otus.hw.dto.TeacherShortDto;
import ru.otus.hw.models.Teacher;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nickname", target = "nickname")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "middleName", target = "middleName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "info", target = "info")
    TeacherDto toDto(Teacher teacher);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nickname", target = "nickname")
    @Mapping(source = ".", target = "fullName", qualifiedByName = "getFullName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "info", target = "info")
    TeacherShortDto toShortDto(Teacher teacher);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nickname", target = "nickname")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "middleName", target = "middleName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "info", target = "info")
    Teacher toEntity(TeacherDto teacherDto);

    List<TeacherDto> toDtos(List<Teacher> teachers);
    List<TeacherShortDto> toShortDtos(List<Teacher> teachers);


    @Named("getFullName")
    default String getFullName(Teacher teacher) {
        return String.format("%s %s %s", teacher.getLastName(), teacher.getFirstName(), teacher.getMiddleName());
    }

}