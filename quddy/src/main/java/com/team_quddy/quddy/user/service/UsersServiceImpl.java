package com.team_quddy.quddy.user.service;

import com.team_quddy.quddy.user.domain.Users;
import com.team_quddy.quddy.user.domain.request.UsersReq;
import com.team_quddy.quddy.user.domain.response.UsersRes;
import com.team_quddy.quddy.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{
    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public String register(HttpServletResponse response, UsersReq usersReq, String usersId, String domain) {
        // 쿠키 있는지 확인
        if (!usersId.equals("NONE")) return "already register : " + usersId;
        Users users = new Users(usersReq.getNickname());
        usersRepository.save(users);
        Integer id = users.getId();
        // 쿠키 생성
        Cookie cookie = new Cookie("usersID", String.valueOf(id));
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(3600 * 24 * 180);     // 1시간 : 3600. 3600 * 24 * 120
        cookie.setSecure(true);
        response.addCookie(cookie);
        return "register success : " + id;
    }

    @Override
    public UsersRes getInfo(String usersId) {
        return usersRepository.getInfo(Integer.parseInt(usersId));
    }
}
