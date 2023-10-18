package com.team_quddy.quddy.exam.domain.dto;

import com.team_quddy.quddy.problem.domain.dto.ProblemResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class ExamDto {
    private String title;
    private List<ProblemResultDto> problems;
}
