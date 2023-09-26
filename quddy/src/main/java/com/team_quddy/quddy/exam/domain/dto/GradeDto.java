package com.team_quddy.quddy.exam.domain.dto;

import com.team_quddy.quddy.problem.domain.dto.ProblemGradeDto;
import lombok.Getter;

import java.util.List;

@Getter
public class GradeDto {
    private Integer id;
    private List<ProblemGradeDto> problems;
}
