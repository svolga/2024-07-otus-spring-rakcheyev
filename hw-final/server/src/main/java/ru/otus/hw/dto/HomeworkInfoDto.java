package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkInfoDto {
    private long id;
    private String name;
    private String info;
    private int mark;
    private String taskName;
    private String groupName;
    private String courseName;
}
