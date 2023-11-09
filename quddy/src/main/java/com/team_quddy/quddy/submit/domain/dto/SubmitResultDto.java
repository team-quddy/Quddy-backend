package com.team_quddy.quddy.submit.domain.dto;

import com.team_quddy.quddy.user.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class SubmitResultDto implements Comparable<SubmitResultDto>{
    private Users users;
    private Long acc;

    @Override
    public int compareTo(SubmitResultDto o) {
        return Long.compare(this.acc, o.acc);
    }
}
