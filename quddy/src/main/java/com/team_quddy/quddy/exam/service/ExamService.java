package com.team_quddy.quddy.exam.service;

import com.team_quddy.quddy.exam.domain.dto.TemplateListDto;
import com.team_quddy.quddy.exam.domain.dto.TemplatePopularDto;
import com.team_quddy.quddy.exam.domain.request.ExamReq;
import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.*;
import com.team_quddy.quddy.global.exception.MyException;
import com.team_quddy.quddy.global.search.SearchOption;
import com.team_quddy.quddy.problem.domain.dto.ProblemResultDto;
import com.team_quddy.quddy.problem.domain.dto.ProblemsDto;

import java.util.List;

public interface ExamService {
    // template -----------------------
    // 공개 문제집 목록 가져오기
    List<TemplateListDto> getTemplate(SearchOption searchOption);
    // 공개 문제집 중 가장 인기있는 5개 목록 가져오기
    List<TemplatePopularDto> getPopularTemplate();

    // solver -----------------------
    // id에 해당하는 공개 문제집의 상세 정보 가져오기
    TemplateDetailRes getTemplateDetail(Integer id);
    // 응시할 문제집 가져오기
    ExamRes<ProblemsDto> getExam(Integer id, String usersId, String secret) throws Exception;
    // 응시 문제집 채점하기
    ResultIdRes getGrade(GradeReq gradeReq, String usersId, String secret) throws Exception;
    // 사용자의 점수 가져오기
    ExamResultRes<ProblemResultDto> getResult(String resultId, String secret) throws Exception;

    // setter -----------------------
    // 문제집 만들기
    ExamIdRes makeExam(ExamReq examReq, String usersId);
    // 자신이 만든 문제집 목록 가져오기
    List<MyExam> getMyExams(SearchOption searchOption, String usersId);
    // 문제집의 통계 정보 가져오기
    ExamStatsRes getStats(Integer id, String userId);
}
