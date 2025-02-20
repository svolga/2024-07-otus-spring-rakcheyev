package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.otus.hw.dto.GroupDto;
import ru.otus.hw.dto.GroupInfoDto;
import ru.otus.hw.models.Course;
import ru.otus.hw.models.Group;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "startAt", target = "startAt")
    @Mapping(source = "endAt", target = "endAt")
    @Mapping(source = "course", target = "courseDto")
    GroupDto toDto(Group group);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "startAt", target = "startAt")
    @Mapping(source = "endAt", target = "endAt")
    @Mapping(source = "course", target = "courseName", qualifiedByName = "getCourseName")
    GroupInfoDto toGroupInfoDto(Group group);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "startAt", target = "startAt")
    @Mapping(source = "endAt", target = "endAt")
    @Mapping(target = "course", ignore = true)
    default Group toEntity(GroupDto groupDto, Course course){
        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());
        group.setInfo(groupDto.getInfo());
        group.setStartAt(groupDto.getStartAt());
        group.setEndAt(groupDto.getEndAt());
        group.setCourse(course);
        return group;
    }

    List<GroupDto> toDtos(List<Group> groups);
    List<GroupInfoDto> toGroupInfoDtos(List<Group> groups);

    @Named("getCourseName")
    default String getCourseName(Course course) {
        return course == null ? "" : course.getName();
    }
}
