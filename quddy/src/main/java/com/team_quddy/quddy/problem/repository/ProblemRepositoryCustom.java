package com.team_quddy.quddy.problem.repository;

import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.problem.domain.dto.ProblemTemplate;

import java.util.List;

public interface ProblemRepositoryCustom {
    List<ProblemTemplate> getProblemTemplate(Exam exam);
}
