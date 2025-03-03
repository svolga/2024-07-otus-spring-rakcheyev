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
public class TeacherDto {
    private long id;

    @NotNull(message = "Заполните поле")
    @Size(min = 2, max = 64, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String nickname;

    @NotBlank(message = "Заполните поле")
    @Size(min = 2, max = 64, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String firstName;

    @Size(min = 2, max = 64, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String middleName;

    @NotBlank(message = "Заполните поле")
    @Size(min = 2, max = 64, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String lastName;

    @Size(min = 2, max = 64, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String phone;

    @Size(min = 2, max = 64, message = "Значение должно быть в диапазоне {min} - {max} ")
    private String email;

    private String info;
}
