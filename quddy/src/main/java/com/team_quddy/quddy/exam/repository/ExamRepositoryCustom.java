package com.team_quddy.quddy.exam.repository;

import com.team_quddy.quddy.exam.domain.response.ExamRes;
import com.team_quddy.quddy.exam.domain.dto.TemplateDto;
import com.team_quddy.quddy.exam.domain.dto.TemplateListDto;
import com.team_quddy.quddy.exam.domain.response.ExamResultRes;
import com.team_quddy.quddy.exam.domain.response.MyExam;
import com.team_quddy.quddy.exam.domain.response.TemplateDetailRes;
import com.team_quddy.quddy.global.search.SearchOption;
import com.team_quddy.quddy.user.domain.Users;

import java.util.List;

public interface ExamRepositoryCustom {
    List<TemplateListDto> getTemplate(SearchOption searchOption);
    List<TemplateDto> getPopularTemplate();

    List<MyExam> getMyExams(SearchOption searchOption, Users users);

    TemplateDetailRes getTemplateDetail(Integer id);

    ExamRes getExam(Integer id);
    ExamResultRes getResult(Integer id, Integer usersId);
}
