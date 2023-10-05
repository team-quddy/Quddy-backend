package com.team_quddy.quddy.user.repository;

import com.team_quddy.quddy.user.domain.response.UsersRes;

public interface UsersRepositoryCustom {
    UsersRes getInfo(Integer id);
}
