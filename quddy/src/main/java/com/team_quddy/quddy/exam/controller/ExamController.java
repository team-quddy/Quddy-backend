package com.team_quddy.quddy.exam.controller;

import com.team_quddy.quddy.exam.domain.response.ExamsRes;
import com.team_quddy.quddy.exam.domain.response.TemplateRes;
import com.team_quddy.quddy.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExamController {
    private final ExamService examService;

    @GetMapping("/template/popular")
    public ExamsRes getPopularTemplate() {
        return new ExamsRes(examService.getPopularTemplate());
    }

}
