package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private long id;
    @NotBlank(message = "Заполните название")
    @Size(min = 2, max = 255, message = "Значение должно быть в диапазоне {min} - {max}")
    private String username;

    private String fullName;

    private String phone;
    private String email;

    private String roleTitles;
}
