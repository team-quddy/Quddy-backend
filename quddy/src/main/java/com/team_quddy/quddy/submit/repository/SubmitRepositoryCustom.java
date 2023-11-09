package com.team_quddy.quddy.submit.repository;

import com.team_quddy.quddy.exam.domain.response.SolveExam;
import com.team_quddy.quddy.submit.domain.Submit;
import com.team_quddy.quddy.user.domain.Users;

import java.util.List;

public interface SubmitRepositoryCustom {
    // 응시했으면 true, 그렇지 않으면 false
    Boolean getSubmit(Integer examId, Integer usersId);

    List<SolveExam> getSolveMyExams(Users users);
}