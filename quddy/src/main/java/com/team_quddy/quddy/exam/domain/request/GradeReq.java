package com.team_quddy.quddy.exam.domain.request;

import com.team_quddy.quddy.problem.domain.dto.ProblemGradeDto;
import lombok.Getter;

import java.util.List;

@Getter
public class GradeReq {
    private Integer id;
    private List<ProblemGradeDto> problems;
}
