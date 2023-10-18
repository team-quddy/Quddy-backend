package com.team_quddy.quddy.exam.domain.response;

import com.team_quddy.quddy.exam.domain.dto.ExamDto;
import com.team_quddy.quddy.exam.domain.dto.ResultDto;
import com.team_quddy.quddy.problem.domain.dto.ProblemResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExamResultRes {
    private ExamDto exam;
    private ResultDto result;
}
