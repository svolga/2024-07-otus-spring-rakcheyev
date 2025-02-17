package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfoDto {
    private long id;
    private int step;
    private int score;
    private String homeworkName;
    private String studentFullName;
    private String rankName;
    private String rankColor;
}
