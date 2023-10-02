package com.team_quddy.quddy.exam.controller;

import com.team_quddy.quddy.exam.domain.request.ExamReq;
import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.*;
import com.team_quddy.quddy.exam.service.ExamService;
import com.team_quddy.quddy.global.search.SearchOption;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExamController {
    private final ExamService examService;
    @PostMapping("template")
    public ExamsRes getTemplate(@RequestBody SearchOption searchOption) {
        return new ExamsRes(examService.getTemplate());
    }


    @GetMapping("/template/popular")
    public ExamsRes getPopularTemplate() {
        return new ExamsRes(examService.getPopularTemplate());
    }

    @GetMapping("/template/{id}")
    public TemplateDetailRes getTemplateDetail(@PathVariable Integer id) {
        return examService.getTemplateDetail(id);
    }

    @GetMapping("/solver/exam/{id}")
    public ExamRes getExam(@PathVariable Integer id) {
        return examService.getExam(id);
    }

    @PostMapping("/solver")
    public GradeRes getGrade(@RequestBody GradeReq gradeReq, @CookieValue(name = "usersID") String usersId) {
        return examService.getGrade(gradeReq, usersId);
    }

    @PostMapping("/setter")
    public ExamIdRes makeExam(@RequestBody ExamReq examReq, @CookieValue(name = "usersID") String usersId) {
        return examService.makeExam(examReq, usersId);
    }

    @PostMapping("/setter/exam")
    public ExamsRes getMyExam(@RequestBody SearchOption searchOption, @CookieValue(name = "usersID") String usersId) {
        return new ExamsRes(examService.getMyExams(searchOption, usersId));
    }

    @GetMapping("/setter/info/{id}")
    public ExamResultRes getResult(@PathVariable(name = "id") Integer id, @CookieValue(name = "usersID") String usersId) {
        return examService.getResult(id, usersId);
    }

}
