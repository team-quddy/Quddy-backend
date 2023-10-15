package com.team_quddy.quddy.exam.controller;

import com.team_quddy.quddy.exam.domain.request.ExamReq;
import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.*;
import com.team_quddy.quddy.exam.service.ExamService;
import com.team_quddy.quddy.global.exception.MyException;
import com.team_quddy.quddy.global.search.SearchOption;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@Api
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExamController {
    private final ExamService examService;
    @GetMapping("template")
    public ExamsRes getTemplate(SearchOption searchOption) {
        return new ExamsRes(examService.getTemplate(searchOption));
    }

    @GetMapping("/template/popular")
    public ExamsRes getPopularTemplate() {
        return new ExamsRes(examService.getPopularTemplate());
    }

    @GetMapping("/template/{id}")
    public ResponseEntity<?> getTemplateDetail(@PathVariable Integer id) {
        log.info("---------------template id : " + id);
        try {
            return new ResponseEntity<>(examService.getTemplateDetail(id), HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/solver/exam/{id}")
    public ExamRes getExam(@PathVariable Integer id) {
        log.info("---------------exam id : " + id);
        return examService.getExam(id);
    }

    @PostMapping("/solver")
    public GradeRes getGrade(@RequestBody GradeReq gradeReq, @CookieValue(name = "usersID") String usersId) {
        log.info("---------------getGrade users id : " + usersId);
        return examService.getGrade(gradeReq, usersId);
    }

    @PostMapping("/setter/exam")
    public ExamIdRes makeExam(@RequestBody ExamReq examReq, @CookieValue(name = "usersID") String usersId) {
        log.info("---------------makeExam users id : " + usersId);
        return examService.makeExam(examReq, usersId);
    }

    @GetMapping("/setter/exam")
    public ExamsRes getMyExam(SearchOption searchOption, @CookieValue(name = "usersID") String usersId) {
        log.info("---------------getMyExam users id : " + usersId);
        return new ExamsRes(examService.getMyExams(searchOption, usersId));
    }

    @GetMapping("/setter/exam/{id}")
    public ResponseEntity<?> getResult(@PathVariable(name = "id") Integer id, @CookieValue(name = "usersID") String usersId) {
        log.info("---------------getResult users id : " + usersId);
        try {
            return new ResponseEntity<>(examService.getResult(id, usersId), HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
