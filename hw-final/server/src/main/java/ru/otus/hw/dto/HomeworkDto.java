package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkDto {
    private long id;
    @NotBlank(message = "Заполните поле")
    @Size(min = 2, max = 255, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String name;
    private String info;
    private int mark;
    @NotNull(message = "Заполните занятие")
    private TaskDto taskDto;
}
