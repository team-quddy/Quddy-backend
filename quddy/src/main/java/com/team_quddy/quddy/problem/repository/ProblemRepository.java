package com.team_quddy.quddy.problem.repository;

import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Integer>, ProblemRepositoryCustom {
}
