package com.team_quddy.quddy.exam.domain.response;

import com.team_quddy.quddy.problem.domain.dto.ProblemTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TemplateDetailRes {
    private String title;
    private String thumbnail;
    private List<ProblemTemplate> problems;
}
