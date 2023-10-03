package com.team_quddy.quddy.user.service;

import com.team_quddy.quddy.user.domain.Users;
import com.team_quddy.quddy.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{
    private final UsersRepository usersRepository;

    @Override
    public String register(Users users) {
        usersRepository.save(users);
        Integer id = users.getId();
        // 암호화
        return String.valueOf(id);
    }

    @Override
    public void setCookie(HttpServletResponse response, String encryptedId, String domain) {
        System.out.println("--------------domain : " + domain);
        Cookie cookie = new Cookie("usersID", encryptedId);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(3600 * 24 * 180);     // 1시간 : 3600. 3600 * 24 * 120
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
