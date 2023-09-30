package com.team_quddy.quddy.exam.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TemplateListDto {
    private String title;
    private String owner;
    private String date;
    private Integer scrap;
    private Integer cnt;
    private String thumbnail;
    private String ref;
    private Integer id;
}
