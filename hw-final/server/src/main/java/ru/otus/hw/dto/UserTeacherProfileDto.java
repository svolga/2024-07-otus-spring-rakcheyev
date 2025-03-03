package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTeacherProfileDto {
    private long id;
    private String username;
    private String fullName;
    private String phone;
    private String email;
    private String profileInfo;
}
