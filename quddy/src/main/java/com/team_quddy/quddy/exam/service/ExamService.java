package com.team_quddy.quddy.exam.service;

import com.team_quddy.quddy.exam.domain.response.TemplateRes;

import java.util.List;

public interface ExamService {
    List<TemplateRes> getPopularTemplate();
}
