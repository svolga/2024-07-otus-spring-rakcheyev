package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankDto {
    private long id;

    @NotBlank(message = "Заполните поле")
    @Size(min = 2, max = 16, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String ukey;

    @NotBlank(message = "Заполните поле")
    @Size(min = 2, max = 32, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String name;

    private String color;
}
