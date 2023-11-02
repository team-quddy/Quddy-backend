package com.team_quddy.quddy.exam.repository;

import com.team_quddy.quddy.exam.domain.response.*;
import com.team_quddy.quddy.exam.domain.dto.TemplateDto;
import com.team_quddy.quddy.exam.domain.dto.TemplateListDto;
import com.team_quddy.quddy.global.search.SearchOption;
import com.team_quddy.quddy.problem.domain.dto.ProblemResultDto;
import com.team_quddy.quddy.problem.domain.dto.ProblemsDto;
import com.team_quddy.quddy.user.domain.Users;

import java.util.List;

public interface ExamRepositoryCustom {
    List<TemplateListDto> getTemplate(SearchOption searchOption);
    List<TemplateDto> getPopularTemplate();

    List<MyExam> getMyExams(SearchOption searchOption, Users users);

    TemplateDetailRes getTemplateDetail(Integer id);

    ExamRes<ProblemsDto> getExam(Integer id);
    ExamStatsRes getStats(Integer id, Integer usersId);
    ExamResultRes<ProblemResultDto> getResult(Integer id, Integer usersId);
}
