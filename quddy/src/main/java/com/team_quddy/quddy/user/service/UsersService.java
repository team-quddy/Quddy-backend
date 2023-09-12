package com.team_quddy.quddy.user.service;

import com.team_quddy.quddy.user.domain.Users;
import com.team_quddy.quddy.user.repository.UsersRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

public interface UsersService {
    String register(Users users);

    void setCookie(HttpServletResponse response, String encryptedId);
}
