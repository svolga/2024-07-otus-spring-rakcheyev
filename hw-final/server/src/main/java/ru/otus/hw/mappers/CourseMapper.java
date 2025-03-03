package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.hw.dto.CourseDto;
import ru.otus.hw.models.Course;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "price", target = "price")
    CourseDto toDto(Course course);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "info", target = "info")
    @Mapping(source = "price", target = "price")
    Course toEntity(CourseDto course);

    List<CourseDto> toDtos(List<Course> courses);
}
