package com.team_quddy.quddy.problem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemResultDto {
    private String question;
    private String answer;
    private String exImg;
    private String exText;
    private Boolean isObjective;
    private String opt;
    private Integer correct;
}
