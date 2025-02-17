package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.ResultDto;
import ru.otus.hw.dto.ResultInfoDto;
import ru.otus.hw.models.Homework;
import ru.otus.hw.models.Rank;
import ru.otus.hw.models.Result;
import ru.otus.hw.models.User;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ResultMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "step", target = "step")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "student", target = "studentDto")
    @Mapping(source = "homework", target = "homeworkDto")
    @Mapping(source = "rank", target = "rankDto")
    ResultDto toDto(Result result);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "step", target = "step")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "student", target = "studentFullName", qualifiedByName = "getStudentFullName")
    @Mapping(source = "homework", target = "homeworkName", qualifiedByName = "getHomeworkName")
    @Mapping(source = "rank", target = "rankName", qualifiedByName = "getRankName")
    @Mapping(source = "rank", target = "rankColor", qualifiedByName = "getRankColor")
    ResultInfoDto toTaskInfoDto(Result result);

    List<ResultDto> toDtos(List<Result> results);

    List<ResultInfoDto> toResultInfoDtos(List<Result> results);

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "homework", ignore = true)
    @Mapping(target = "rank", ignore = true)
    default Result toEntity(ResultDto resultDto, User student, Homework homework, Rank rank) {
        return Result.builder()
                .id(resultDto.getId())
                .score(resultDto.getScore())
                .step(resultDto.getStep())
                .student(student)
                .homework(homework)
                .rank(rank)
                .build();
    }

    @Named("getHomeworkName")
    default String getHomeworkName(Homework homework) {
        return homework == null ? "" : homework.getName();
    }

    @Named("getRankName")
    default String getRankName(Rank rank) {
        return rank == null ? "" : rank.getName();
    }

    @Named("getRankColor")
    default String getRankColor(Rank rank) {
        return rank == null ? "" : rank.getColor();
    }

    @Named("getStudentFullName")
    default String getStudentFullName(User student) {
        return String.format("%s %s %s", student.getLastName(), student.getFirstName(), student.getMiddleName());
    }

}
