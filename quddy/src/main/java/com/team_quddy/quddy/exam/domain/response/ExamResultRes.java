package com.team_quddy.quddy.exam.domain.response;

import com.team_quddy.quddy.problem.domain.dto.ProblemResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExamResultRes {
    private String title;
    private String thumbnail;
    private String date;
    private Integer cnt;
    private Integer scrap;
    private String nickname;
    private String ref;
    private List<ProblemResultDto> problems;
}
