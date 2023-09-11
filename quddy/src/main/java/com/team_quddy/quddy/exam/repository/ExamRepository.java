package com.team_quddy.quddy.exam.repository;

import com.team_quddy.quddy.exam.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Integer>, ExamRepositoryCustom{

}
