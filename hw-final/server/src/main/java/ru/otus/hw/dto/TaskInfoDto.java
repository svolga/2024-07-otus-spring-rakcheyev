package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfoDto {
    private long id;
    @NotBlank(message = "Заполните название")
    @Size(min = 2, max = 255, message = "Значение должно быть в диапазоне {min} - {max}")
    private String name;
    private String info;
    private LocalDateTime startAt;
    private String teacherFullName;
    private String groupName;
    private String courseName;
    private List<TaskMaterialDto> taskMaterialDtoList;
}
