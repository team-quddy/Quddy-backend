package com.team_quddy.quddy.exam.domain.response;

import com.team_quddy.quddy.exam.domain.dto.ExamDto;
import com.team_quddy.quddy.problem.domain.dto.ProblemsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class ExamRes {
    private String id;
    private ExamDto exam;
}
