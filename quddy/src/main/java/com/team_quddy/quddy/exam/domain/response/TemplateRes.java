package com.team_quddy.quddy.exam.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class TemplateRes {
    private String title;
    private String date;
    private Integer scrap;
    private Integer cnt;
    private String thumbnail;
    private String ref;
    private String id;
}
