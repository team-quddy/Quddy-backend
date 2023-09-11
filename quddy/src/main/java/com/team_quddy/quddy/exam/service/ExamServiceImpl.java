package com.team_quddy.quddy.exam.service;

import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.exam.domain.dto.TemplateDto;
import com.team_quddy.quddy.exam.domain.response.TemplateDetailRes;
import com.team_quddy.quddy.exam.domain.response.TemplateRes;
import com.team_quddy.quddy.exam.repository.ExamRepository;
import com.team_quddy.quddy.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService{
    private final ExamRepository examRepository;
    private final ProblemRepository problemRepository;

    @Override
    public List<TemplateRes> getPopularTemplate() {
        List<TemplateDto> templateDtoList = examRepository.getPopularTemplate();
        List<TemplateRes> templateResList = templateDtoList.stream().map(tp -> new TemplateRes(tp.getTitle(), tp.getDate(), tp.getScrap(), tp.getCnt(), tp.getThumbnail(),
                String.valueOf(tp.getRef()), String.valueOf(tp.getId()))).collect(Collectors.toList());
        //String.valueOf가 아니라 암호화
        return templateResList;
    }

    @Override
    public TemplateDetailRes getTemplateDetail(Integer id) {
        Exam exam = examRepository.getExamById(id);

        return new TemplateDetailRes(exam.getTitle(), exam.getThumbnail(), problemRepository.getProblemTemplate(exam));
    }
}
