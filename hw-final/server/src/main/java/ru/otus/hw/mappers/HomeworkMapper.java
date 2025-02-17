package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.otus.hw.dto.HomeworkDto;
import ru.otus.hw.dto.HomeworkInfoDto;
import ru.otus.hw.models.Homework;
import ru.otus.hw.models.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HomeworkMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "mark", target = "mark")
    @Mapping(source = "task", target = "taskDto")
    HomeworkDto toDto(Homework homework);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "mark", target = "mark")
    @Mapping(source = "task", target = "courseName", qualifiedByName = "getCourseName")
    @Mapping(source = "task", target = "groupName", qualifiedByName = "getGroupName")
    @Mapping(source = "task", target = "taskName", qualifiedByName = "getTaskName")
    HomeworkInfoDto toHomeworkInfoDto(Homework homework);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "mark", target = "mark")
    @Mapping(target = "task", ignore = true)
    default Homework toEntity(HomeworkDto homeworkDto, Task task) {
        return Homework.builder()
                .id(homeworkDto.getId())
                .name(homeworkDto.getName())
                .info(homeworkDto.getInfo())
                .mark(homeworkDto.getMark())
                .task(task)
                .build();
    }

    List<HomeworkDto> toDtos(List<Homework> homeworks);

    List<HomeworkInfoDto> toHomeworkInfoDtos(List<Homework> homeworks);

    @Named("getTaskName")
    default String getTaskName(Task task) {
        return task == null ? "" : task.getName();
    }

    @Named("getGroupName")
    default String getGroupName(Task task) {
        return task.getGroup() == null ? "" : task.getGroup().getName();
    }

    @Named("getCourseName")
    default String getCourseName(Task task) {
        return task.getGroup() == null && task.getGroup().getCourse() == null ? "" : task.getGroup().getCourse().getName();
    }

}
