package com.team_quddy.quddy.user.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsersRes {
    private String nickname;
    private String id;
    private Integer examCnt;
    private Integer publicExamCnt;
    private Integer scrapCnt;
}
