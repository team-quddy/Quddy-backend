package com.team_quddy.quddy.exam.service;

import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.exam.domain.dto.ExamDto;
import com.team_quddy.quddy.exam.domain.response.ExamRes;
import com.team_quddy.quddy.exam.domain.dto.TemplateDto;
import com.team_quddy.quddy.exam.domain.dto.TemplateListDto;
import com.team_quddy.quddy.exam.domain.dto.TemplatePopularDto;
import com.team_quddy.quddy.exam.domain.request.ExamReq;
import com.team_quddy.quddy.exam.domain.request.GradeReq;
import com.team_quddy.quddy.exam.domain.response.*;
import com.team_quddy.quddy.exam.repository.ExamRepository;
import com.team_quddy.quddy.global.cipher.CipherService;
import com.team_quddy.quddy.global.exception.MyException;
import com.team_quddy.quddy.global.search.SearchOption;
import com.team_quddy.quddy.problem.domain.Problem;
import com.team_quddy.quddy.problem.domain.dto.ProblemGradeDto;
import com.team_quddy.quddy.problem.domain.dto.ProblemTemplate;
import com.team_quddy.quddy.problem.repository.ProblemRepository;
import com.team_quddy.quddy.submit.domain.Submit;
import com.team_quddy.quddy.submit.repository.SubmitRepository;
import com.team_quddy.quddy.user.domain.Users;
import com.team_quddy.quddy.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    private final CipherService cipherService;

    @Override
    public List<TemplateListDto> getTemplate(SearchOption searchOption) {
        return examRepository.getTemplate(searchOption);
    }

    @Override
    public List<TemplatePopularDto> getPopularTemplate() {
        List<TemplateDto> templateDtoList = examRepository.getPopularTemplate();
        List<TemplatePopularDto> templatePopularDtoList = templateDtoList.stream().map(tp -> new TemplatePopularDto(tp.getTitle(), tp.getDate(), tp.getScrap(), tp.getCnt(), tp.getThumbnail(),
                tp.getRef(), String.valueOf(tp.getId()))).collect(Collectors.toList());
        //String.valueOf가 아니라 암호화
        return templatePopularDtoList;
    }

    @Override
    public TemplateDetailRes getTemplateDetail(Integer id) throws MyException{
        return examRepository.getTemplateDetail(id);
    }

    @Override
    public ExamRes getExam(Integer id, String usersId, String secret) throws Exception{
        ExamRes exam = examRepository.getExam(id);
        Boolean isSolved = submitRepository.getSubmit(id, Integer.parseInt(usersId));
        String result = null;
        if (isSolved) {
            result = cipherService.encodeResultId(id, usersId, secret);
            return new ExamRes(new ExamDto(result, null));
        }
        return exam;
    }

    @Override
    @Transactional
    public ResultIdRes getGrade(GradeReq gradeReq, String usersId, String secret) throws Exception{
        Boolean isSolved = submitRepository.getSubmit(gradeReq.getId(), Integer.parseInt(usersId));
        if (isSolved) {
            throw new MyException("잘못된 접근입니다 : 이미 응시한 시험입니다.");
        }
        Exam exam = examRepository.getExamById(gradeReq.getId());
        if (gradeReq.getProblems().size() != exam.getCnt()) {
            throw new MyException("잘못된 요청입니다 : 제출 정답의 개수가 다릅니다.");
        }
        Users users = usersRepository.getUsersById(Integer.parseInt(usersId));
        // exam의 problems랑, greadeReq의 problems랑 answer 비교하기
        List<ProblemGradeDto> sheet = gradeReq.getProblems();
        Collections.sort(sheet, ((o1, o2) -> Integer.compare(Integer.parseInt(o1.getProblemId()), Integer.parseInt(o2.getProblemId()))));
        Collections.sort(exam.getProblems(), (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        for (int i = 0; i < sheet.size(); i++) {
            Boolean isCorrect = false;
            if (sheet.get(i).getAnswer().equals(exam.getProblems().get(i).getAnswer())) {
                exam.getProblems().get(i).addCnt();
                isCorrect = true;
            }
            Submit submit = new Submit(sheet.get(i).getAnswer(), isCorrect, exam.getProblems().get(i), users);
            submitRepository.save(submit);
        }
        return new ResultIdRes(cipherService.encodeResultId(gradeReq.getId(), usersId, secret));
    }

    @Override
    @Transactional
    public ExamIdRes makeExam(ExamReq examReq, String usersId) {
        Users users = usersRepository.getUsersById(Integer.parseInt(usersId));
        Exam exam = examRepository.save(Exam.createExam(examReq.getTitle(), examReq.getDate(), examReq.getIsPublic(), examReq.getProblems().size(), examReq.getRef(), examReq.getThumbnail(), users));

        for (int i = 0; i < examReq.getProblems().size(); i++) {
            ProblemTemplate template = examReq.getProblems().get(i);
            problemRepository.save(Problem.createProblem(template.getQuestion(), template.getAnswer(), template.getIsObjective(), template.getExImg(), template.getExText(), template.getOpt(), exam));
        }
        return new ExamIdRes(String.valueOf(exam.getId()));
    }

    @Override
    public List<MyExam> getMyExams(SearchOption searchOption, String usersId) {
        Users users = usersRepository.getUsersById(Integer.parseInt(usersId));

        return examRepository.getMyExams(searchOption, users);
    }

    @Override
    public ExamResultRes getResult(String resultId, String secret) throws Exception {
        String[] plain = cipherService.decode(resultId, secret).split(" ");
        log.info("exam id : " + plain[0] + " users id : " + plain[1]);
        return examRepository.getResult(Integer.parseInt(plain[0]), Integer.parseInt(plain[1]));
    }

    @Override
    public ExamStatsRes getStats(Integer id, String userId) throws MyException{
        return examRepository.getStats(id, Integer.valueOf(userId));
    }
}
