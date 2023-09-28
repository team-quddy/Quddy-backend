package com.team_quddy.quddy.exam.domain.request;

import com.team_quddy.quddy.problem.domain.dto.ProblemTemplate;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;

@Data
@Getter
public class ExamReq {
    private String title;
    private Boolean isPublic;
    private Integer cnt;
    private String date;
    private String thumbnail;
    private String ref;
    private ArrayList<ProblemTemplate> problems;
}
