package com.team_quddy.quddy.user.controller;

import com.team_quddy.quddy.user.domain.Users;
import com.team_quddy.quddy.user.domain.request.UsersReq;
import com.team_quddy.quddy.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UsersController {
    private final UsersService usersService;

    @PostMapping()
    public String registerUsers(HttpServletResponse response, @RequestBody UsersReq usersReq,
                                @CookieValue(name = "usersID", defaultValue = "NONE") String usersID, @Value("${myapp.domain}") String domain) {
        // 쿠키가 있는지 확인
        if (usersID.equals("NONE")) {
            // 없으면 사용자 등록 후 cookie 추가
            usersService.setCookie(response, usersService.register(new Users(usersReq.getNickname())), domain);
            return "register success";
        }
        return "already register";
    }
}
