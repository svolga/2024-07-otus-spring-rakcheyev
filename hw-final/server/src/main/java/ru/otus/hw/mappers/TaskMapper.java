package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.TaskDto;
import ru.otus.hw.dto.TaskInfoDto;
import ru.otus.hw.models.Group;
import ru.otus.hw.models.Task;
import ru.otus.hw.models.Teacher;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface TaskMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "target", target = "target")
    @Mapping(source = "shortInfo", target = "shortInfo")
    @Mapping(source = "startAt", target = "startAt")
    @Mapping(source = "teacher", target = "teacherDto")
    @Mapping(source = "group", target = "groupDto")
    TaskDto toDto(Task task);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "target", target = "target")
    @Mapping(source = "shortInfo", target = "shortInfo")
    @Mapping(source = "startAt", target = "startAt")
    @Mapping(source = "teacher", target = "teacherName", qualifiedByName = "getTeacherFullName")
    @Mapping(source = "group", target = "groupName", qualifiedByName = "getGroupName")
    TaskInfoDto toTaskInfoDto(Task task);

    List<TaskDto> toDtos(List<Task> tasks);

    List<TaskInfoDto> toTaskInfoDtos(List<Task> tasks);

    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "group", ignore = true)
    default Task toEntity(TaskDto taskDto, Teacher teacher, Group group) {
        return Task.builder()
                .id(taskDto.getId())
                .name(taskDto.getName())
                .info(taskDto.getInfo())
                .result(taskDto.getResult())
                .shortInfo(taskDto.getShortInfo())
                .startAt(taskDto.getStartAt())
                .target(taskDto.getTarget())
                .teacher(teacher)
                .group(group)
                .build();
    }

    @Named("getGroupName")
    default String getGroupName(Group group) {
        return group == null ? "" : group.getName();
    }

    @Named("getTeacherFullName")
    default String getTeacherFullName(Teacher teacher) {
        return String.format("%s %s %s", teacher.getLastName(), teacher.getFirstName(), teacher.getMiddleName());
    }

}
