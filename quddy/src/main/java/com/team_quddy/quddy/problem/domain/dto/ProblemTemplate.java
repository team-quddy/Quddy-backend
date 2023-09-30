package com.team_quddy.quddy.problem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProblemTemplate {
    private String question;
    private String answer;
    private String exImg;
    private String exText;
    private Boolean objective;
    private String opt;
}
