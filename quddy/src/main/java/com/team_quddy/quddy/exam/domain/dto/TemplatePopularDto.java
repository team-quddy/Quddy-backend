package com.team_quddy.quddy.exam.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TemplatePopularDto {
    private String title;
    private String date;
    private Integer scrap;
    private Integer cnt;
    private String thumbnail;
    private String ref;
    private String id;
}
