package com.team_quddy.quddy.exam.service;

import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.ExamRes;
import com.team_quddy.quddy.exam.domain.response.GradeRes;
import com.team_quddy.quddy.exam.domain.response.TemplateDetailRes;
import com.team_quddy.quddy.exam.domain.response.TemplateRes;

import java.util.List;

public interface ExamService {
    List<TemplateRes> getPopularTemplate();

    TemplateDetailRes getTemplateDetail(Integer id);

    ExamRes getExam(Integer id);

    GradeRes getGrade(GradeReq gradeReq, String usersId);
}
