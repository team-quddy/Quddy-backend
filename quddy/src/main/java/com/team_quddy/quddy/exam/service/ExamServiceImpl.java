package com.team_quddy.quddy.exam.service;

import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.exam.domain.dto.TemplateDto;
import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.ExamRes;
import com.team_quddy.quddy.exam.domain.response.GradeRes;
import com.team_quddy.quddy.exam.domain.response.TemplateDetailRes;
import com.team_quddy.quddy.exam.domain.response.TemplateRes;
import com.team_quddy.quddy.exam.repository.ExamRepository;
import com.team_quddy.quddy.problem.domain.dto.ProblemGradeDto;
import com.team_quddy.quddy.problem.repository.ProblemRepository;
import com.team_quddy.quddy.submit.domain.Submit;
import com.team_quddy.quddy.submit.repository.SubmitRepository;
import com.team_quddy.quddy.user.domain.Users;
import com.team_quddy.quddy.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamServiceImpl implements ExamService{
    private final ExamRepository examRepository;
    private final ProblemRepository problemRepository;
    private final UsersRepository usersRepository;
    private final SubmitRepository submitRepository;

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

    @Override
    public ExamRes getExam(Integer id) {
        Exam exam = examRepository.getExamById(id);

        return new ExamRes(exam.getTitle(), exam.getCreatedDate(), problemRepository.getProblems(exam));
    }

    @Override
    public GradeRes getGrade(GradeReq gradeReq, String usersId) {
        Exam exam = examRepository.getExamById(gradeReq.getExam().getId());
        Users users = usersRepository.getUsersById(Integer.parseInt(usersId));
        // exam의 problems랑, greadeReq의 problems랑 answer 비교하기
        List<ProblemGradeDto> sheet = gradeReq.getExam().getProblems();
        Collections.sort(sheet, ((o1, o2) -> Integer.compare(Integer.parseInt(o1.getProblemId()), Integer.parseInt(o2.getProblemId()))));
        Collections.sort(exam.getProblems(), (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        Integer correct = 0;    // correct : 맞은 문제 개수
        for (int i = 0; i < sheet.size(); i++) {
            Boolean isCorrect = false;
            if (sheet.get(i).getAnswer().equals(exam.getProblems().get(i).getAnswer())) {
                correct++;
                isCorrect = true;
            }
            Submit submit = new Submit(sheet.get(i).getAnswer(), isCorrect, exam.getProblems().get(i), users);
            submitRepository.save(submit);
        }
        return new GradeRes(correct);
    }
}
