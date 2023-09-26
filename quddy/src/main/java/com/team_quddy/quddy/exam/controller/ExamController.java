package com.team_quddy.quddy.exam.controller;

import com.team_quddy.quddy.exam.domain.request.ExamReq;
import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.ExamRes;
import com.team_quddy.quddy.exam.domain.response.ExamsRes;
import com.team_quddy.quddy.exam.domain.response.GradeRes;
import com.team_quddy.quddy.exam.domain.response.TemplateDetailRes;
import com.team_quddy.quddy.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExamController {
    private final ExamService examService;

    @GetMapping("/template/popular")
    public ExamsRes getPopularTemplate() {
        return new ExamsRes(examService.getPopularTemplate());
    }

    @GetMapping("/template/detail")
    public TemplateDetailRes getTemplateDetail(@RequestBody ExamReq examReq) {
        return examService.getTemplateDetail(examReq.getId());
    }

    @GetMapping("/solver/exam")
    public ExamRes getExam(@RequestBody ExamReq examReq) {
        return examService.getExam(examReq.getId());
    }

    @PostMapping("/solver")
    public GradeRes getGrade(@RequestBody GradeReq gradeReq, @CookieValue(name = "usersID") String usersId) {
        return examService.getGrade(gradeReq, usersId);
    }
}
