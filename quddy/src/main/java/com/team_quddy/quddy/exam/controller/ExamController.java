package com.team_quddy.quddy.exam.controller;

import com.team_quddy.quddy.exam.domain.request.ExamReq;
import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.*;
import com.team_quddy.quddy.exam.service.ExamService;
import com.team_quddy.quddy.global.exception.MyException;
import com.team_quddy.quddy.global.search.SearchOption;
import com.team_quddy.quddy.problem.domain.dto.ProblemsDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExamController {
    private final ExamService examService;

    /**
     * 공개 문제집 목록 가져오기
     * @param searchOption : 정렬 옵션
     * @return 공개 문제집 목록
     */
    @GetMapping("template")
    public ExamsRes getTemplate(SearchOption searchOption) {
        return new ExamsRes(examService.getTemplate(searchOption));
    }

    /**
     * 공개 문제집 중 가장 인기있는 5개 목록 가져오기
     * @return 인기 공개 문제집 목록
     */
    @GetMapping("/template/popular")
    public ExamsRes getPopularTemplate() {
        return new ExamsRes(examService.getPopularTemplate());
    }

    /**
     * id에 해당하는 공개 문제집의 상세 정보 가져오기
     * @param id : 공개 문제집 id
     * @return 해당 공개 문제집의 상세 정보
     */
    @GetMapping("/template/{id}")
    public ResponseEntity<?> getTemplateDetail(@PathVariable Integer id) {
        log.info("---------------template id : " + id);
        try {
            return new ResponseEntity<>(examService.getTemplateDetail(id), HttpStatus.OK);
        } catch (MyException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 응시할 문제집 가져오기
     * @param id : 응시할 문제집 id
     * @param usersId : 사용자 id
     * @param secret : 암호화 키
     * @return 응시했을 경우 result id를, 응시하지 않았거나 비회원인 경우 응시 문제집 상세 정보
     */
    @GetMapping("/solver/exam/{id}")
    public ResponseEntity<?> getExam(@PathVariable Integer id, @CookieValue(name = "usersID", defaultValue = "noID") String usersId, @Value("${myapp.secret}") String secret) {
        log.info("---------------exam id : " + id);
        try {
            ExamRes<ProblemsDto> exam = examService.getExam(id, usersId, secret);
            if (exam.getExam().getProblems() == null) {
                return new ResponseEntity<>(new ResultIdRes(exam.getExam().getTitle()), HttpStatus.OK);
            }
            return new ResponseEntity<>(exam, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 응시 문제집 채점하기
     * @param gradeReq : 사용자가 응시한 정보
     * @param usersId : 사용자 id
     * @param secret : 암호화 키
     * @return result id
     */
    @PostMapping("/solver/exam")
    public ResponseEntity<?> getGrade(@RequestBody GradeReq gradeReq, @CookieValue(name = "usersID") String usersId, @Value("${myapp.secret}") String secret) {
        log.info("---------------getGrade users id : " + usersId + " exam id : " + gradeReq.getId());
        try {
            ResultIdRes gradeRes = examService.getGrade(gradeReq, usersId, secret);
            return new ResponseEntity<ResultIdRes>(gradeRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 사용자의 점수 가져오기
     * @param resultId : 응시 결과 id
     * @param secret : 암호화 키
     * @return 응시 결과 정보
     */
    @GetMapping("/solver/result/{resultId}")
    public ResponseEntity<?> getResult(@PathVariable(name = "resultId") String resultId, @Value("${myapp.secret}") String secret) {
        log.info("------------getResult");
        try {
            return new ResponseEntity<>(examService.getResult(resultId, secret), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 문제집 만들기
     * @param examReq : 만들 문제집 상세 정보
     * @param usersId : 사용자 id
     * @return 만들어진 문제집 id
     */
    @PostMapping("/setter/exam")
    public ExamIdRes makeExam(@RequestBody ExamReq examReq, @CookieValue(name = "usersID") String usersId) {
        log.info("---------------makeExam users id : " + usersId);
        return examService.makeExam(examReq, usersId);
    }

    /**
     * 자신이 만든 문제집 목록 가져오기
     * @param searchOption : 정렬 옵션
     * @param usersId : 사용자 id
     * @return 자신이 만든 문제집 목록
     */
    @GetMapping("/setter/exam")
    public ExamsRes getMyExam(SearchOption searchOption, @CookieValue(name = "usersID") String usersId) {
        log.info("---------------getMyExam users id : " + usersId);
        return new ExamsRes(examService.getMyExams(searchOption, usersId));
    }

    /**
     * 문제집의 통계 정보 가져오기
     * @param id : 문제집 id
     * @param usersId : 사용자 id
     * @return 해당 문제집의 통계 정보
     */
    @GetMapping("/setter/exam/{id}")
    public ResponseEntity<?> getStats(@PathVariable(name = "id") Integer id, @CookieValue(name = "usersID") String usersId) {
        log.info("---------------getStats users id : " + usersId);
        try {
            return new ResponseEntity<>(examService.getStats(id, usersId), HttpStatus.OK);
        } catch (MyException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
