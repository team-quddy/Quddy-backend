package com.team_quddy.quddy.submit.repository;

import com.team_quddy.quddy.submit.domain.Submit;

public interface SubmitRepositoryCustom {
    // 응시했으면 true, 그렇지 않으면 false
    Boolean getSubmit(Integer examId, Integer usersId);
}
