package com.team_quddy.quddy.user.controller;

import com.team_quddy.quddy.user.domain.Users;
import com.team_quddy.quddy.user.domain.request.UsersReq;
import com.team_quddy.quddy.user.domain.response.UsersRes;
import com.team_quddy.quddy.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UsersController {
    private final UsersService usersService;

    @PostMapping()
    public String registerUsers(HttpServletResponse response, @RequestBody UsersReq usersReq,
                                @CookieValue(name = "usersID", defaultValue = "NONE") String usersID, @Value("${myapp.domain}") String domain) {
        return usersService.register(response, usersReq, usersID, domain);
    }
    @GetMapping("/users")
    public UsersRes getInfo(@CookieValue(name = "usersID") String usersId) {
        log.info("---------------getInfo users id : " + usersId);
        return usersService.getInfo(usersId);
    }
}
