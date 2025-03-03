package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupInfoDto {
    private long id;
    private String name;
    private String info;
    private LocalDate startAt;
    private LocalDate endAt;
    private String courseName;
}
