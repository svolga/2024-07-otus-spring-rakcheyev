package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskMaterialDto {

    private long id;
    private long taskId;

    @NotBlank(message = "Заполните поле")
    @Size(min = 2, max = 255, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String name;

    @NotBlank(message = "Заполните поле")
    @Size(min = 2, max = 255, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String url;
}
