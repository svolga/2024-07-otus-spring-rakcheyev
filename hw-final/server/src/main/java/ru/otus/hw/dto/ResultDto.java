package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    private long id;
    private int step;
    private int score;
    private HomeworkDto homeworkDto;
    private UserDto studentDto;
    private RankDto rankDto;
}
