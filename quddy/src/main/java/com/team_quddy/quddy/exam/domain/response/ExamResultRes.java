package com.team_quddy.quddy.exam.domain.response;

import com.team_quddy.quddy.exam.domain.dto.ExamDto;
import com.team_quddy.quddy.exam.domain.dto.ResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ExamResultRes {
    private ExamDto exam;
    private ResultDto result;
}
