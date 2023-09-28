package com.team_quddy.quddy.exam.domain.response;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MyExam {
    private String title;
    private String date;
    private Boolean isPublic;
    private Integer scrap;
    private Integer cnt;
    private String thumbnail;
    private Integer id;
}
