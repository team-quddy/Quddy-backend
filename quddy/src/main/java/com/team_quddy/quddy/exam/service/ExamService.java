package com.team_quddy.quddy.exam.service;

import com.team_quddy.quddy.exam.domain.request.ExamReq;
import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.*;

import javax.transaction.Transactional;
import java.util.List;

public interface ExamService {
    List<TemplateRes> getPopularTemplate();

    TemplateDetailRes getTemplateDetail(Integer id);

    ExamRes getExam(Integer id);

    GradeRes getGrade(GradeReq gradeReq, String usersId);

    ExamIdRes makeExam(ExamReq examReq, String usersId);
}
