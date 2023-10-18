package com.team_quddy.quddy.problem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProblemResultTempDto {
    private Integer id;
    private String question;
    private String answer;
    private String exImg;
    private String exText;
    private Boolean isObjective;
    private String opt;
    private String submitAnswer;
    private Boolean isCorrect;
}
