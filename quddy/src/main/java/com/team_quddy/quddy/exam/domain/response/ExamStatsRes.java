package com.team_quddy.quddy.exam.domain.response;

import com.team_quddy.quddy.problem.domain.dto.ProblemStatsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExamStatsRes {
    private String title;
    private String thumbnail;
    private String date;
    private Boolean isPublic;
    private Integer cnt;
    private Integer total;
    private Integer scrap;
    private String nickname;
    private String ref;
    private List<ProblemStatsDto> problems;
}
