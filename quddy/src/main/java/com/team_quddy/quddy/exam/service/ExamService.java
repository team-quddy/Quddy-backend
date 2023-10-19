package com.team_quddy.quddy.exam.service;

import com.team_quddy.quddy.exam.domain.dto.TemplateListDto;
import com.team_quddy.quddy.exam.domain.dto.TemplatePopularDto;
import com.team_quddy.quddy.exam.domain.request.ExamReq;
import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.*;
import com.team_quddy.quddy.global.exception.MyException;
import com.team_quddy.quddy.global.search.SearchOption;

import java.util.List;

public interface ExamService {
    List<TemplateListDto> getTemplate(SearchOption searchOption);
    List<TemplatePopularDto> getPopularTemplate();

    TemplateDetailRes getTemplateDetail(Integer id);

    ExamRes getExam(Integer id, String usersId, String secret) throws Exception;

    GradeRes getGrade(GradeReq gradeReq, String usersId) throws MyException;

    ExamIdRes makeExam(ExamReq examReq, String usersId);

    List<MyExam> getMyExams(SearchOption searchOption, String usersId);

    ExamResultRes getResult(String resultId, String secret) throws Exception;
    ExamStatsRes getStats(Integer id, String userId);
}
