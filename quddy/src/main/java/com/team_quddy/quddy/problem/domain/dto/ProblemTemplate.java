package com.team_quddy.quddy.problem.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProblemTemplate {
    private String question;
    private String answer;
    private String exImg;
    private String exText;
    private boolean isObjective;
    private String opt;
}
