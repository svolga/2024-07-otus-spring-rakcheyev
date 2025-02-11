package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private long id;
    @NotBlank(message = "Заполните название")
    @Size(min = 2, max = 255, message = "Значение должно быть в диапазоне {min} - {max}")
    private String name;

    private String info;
    private String target;
    private String shortInfo;
    private String result;
    private LocalDateTime startAt;
    private TeacherDto teacherDto;
    private GroupDto groupDto;
}
