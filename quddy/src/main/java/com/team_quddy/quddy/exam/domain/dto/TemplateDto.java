package com.team_quddy.quddy.exam.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TemplateDto {
    private String title;
    private String date;
    private Integer scrap;
    private Integer cnt;
    private String thumbnail;
    private String ref;
    private Integer id;
}
