package com.team_quddy.quddy.user.service;

import com.team_quddy.quddy.user.domain.Users;
import com.team_quddy.quddy.user.domain.request.UsersReq;
import com.team_quddy.quddy.user.domain.response.UsersRes;
import com.team_quddy.quddy.user.repository.UsersRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

public interface UsersService {
    String register(HttpServletResponse response, UsersReq usersReq, String usersId, String domain);

    UsersRes getInfo(String usersId);

}
