package com.team_quddy.quddy.submit.domain.dto;

import com.team_quddy.quddy.user.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class SubmitResultDto {
    private Users users;
    private Integer acc;
}
