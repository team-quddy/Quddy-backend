package com.team_quddy.quddy.exam.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultDto {
    private Integer problemCnt;
    private Integer correct;
    private Double percentile;
    private Boolean firstSolver;
}
