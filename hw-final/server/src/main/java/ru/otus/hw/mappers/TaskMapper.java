package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.TaskDto;
import ru.otus.hw.dto.TaskInfoDto;
import ru.otus.hw.dto.TaskMaterialDto;
import ru.otus.hw.models.Group;
import ru.otus.hw.models.Task;
import ru.otus.hw.models.TaskMaterial;
import ru.otus.hw.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface TaskMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "startAt", target = "startAt")
    @Mapping(source = "teacher", target = "teacherDto")
    @Mapping(source = "group", target = "groupDto")
    TaskDto toDto(Task task);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "startAt", target = "startAt")
    @Mapping(source = "teacher", target = "teacherFullName", qualifiedByName = "getTeacherFullName")
    @Mapping(source = "group", target = "groupName", qualifiedByName = "getGroupName")
    @Mapping(source = "group", target = "courseName", qualifiedByName = "getCourseName")
    @Mapping(source = "taskMaterials", target = "taskMaterialDtoList", qualifiedByName = "getTaskMaterials")
    TaskInfoDto toTaskInfoDto(Task task);

    List<TaskDto> toDtos(List<Task> tasks);

    List<TaskInfoDto> toTaskInfoDtos(List<Task> tasks);

    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "group", ignore = true)
    default Task toEntity(TaskDto taskDto, User teacher, Group group) {
        return Task.builder()
                .id(taskDto.getId())
                .name(taskDto.getName())
                .info(taskDto.getInfo())
                .startAt(taskDto.getStartAt())
                .teacher(teacher)
                .group(group)
                .build();
    }

    @Named("getGroupName")
    default String getGroupName(Group group) {
        return group == null ? "" : group.getName();
    }

    @Named("getCourseName")
    default String getCourseName(Group group) {

        if (group == null) {
            return "";
        }

        return group.getCourse() == null ? "" : group.getCourse().getName();
    }

    @Named("getTeacherFullName")
    default String getTeacherFullName(User teacher) {
        return String.format("%s %s %s", teacher.getLastName(), teacher.getFirstName(), teacher.getMiddleName());
    }

    @Named("getTaskMaterials")
    default List<TaskMaterialDto> getTaskMaterials(List<TaskMaterial> taskMaterials) {
        return taskMaterials.stream()
                .map(tm ->
                        TaskMaterialDto.builder()
                                .taskId(tm.getTask().getId())
                                .url(tm.getUrl())
                                .name(tm.getName())
                                .id(tm.getId())
                                .build())
                .collect(Collectors.toList());
    }

}
