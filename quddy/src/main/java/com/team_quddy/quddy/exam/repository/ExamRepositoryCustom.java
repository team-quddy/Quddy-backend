package com.team_quddy.quddy.exam.repository;

import com.team_quddy.quddy.exam.domain.dto.TemplateDto;

import java.util.List;

public interface ExamRepositoryCustom {
    List<TemplateDto> getPopularTemplate();
}
