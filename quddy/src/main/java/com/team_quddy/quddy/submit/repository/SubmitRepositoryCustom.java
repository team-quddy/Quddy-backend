package com.team_quddy.quddy.submit.repository;

import com.team_quddy.quddy.submit.domain.Submit;

public interface SubmitRepositoryCustom {
    Boolean getSubmit(Integer examId, Integer usersId);
}
