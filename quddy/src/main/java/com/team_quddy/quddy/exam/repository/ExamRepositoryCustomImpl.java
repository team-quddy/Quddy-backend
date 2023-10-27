package com.team_quddy.quddy.exam.repository;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.exam.domain.QExam;
import com.team_quddy.quddy.exam.domain.dto.ExamDto;
import com.team_quddy.quddy.exam.domain.dto.ResultDto;
import com.team_quddy.quddy.exam.domain.response.*;
import com.team_quddy.quddy.exam.domain.dto.TemplateDto;
import com.team_quddy.quddy.exam.domain.dto.TemplateListDto;
import com.team_quddy.quddy.global.exception.MyException;
import com.team_quddy.quddy.global.search.SearchOption;
import com.team_quddy.quddy.problem.domain.QProblem;
import com.team_quddy.quddy.problem.domain.dto.*;
import com.team_quddy.quddy.submit.domain.QSubmit;
import com.team_quddy.quddy.submit.domain.dto.SubmitResultDto;
import com.team_quddy.quddy.user.domain.QUsers;
import com.team_quddy.quddy.user.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.team_quddy.quddy.exam.domain.QExam.exam;


@Slf4j
public class ExamRepositoryCustomImpl implements ExamRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ExamRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<TemplateListDto> getTemplate(SearchOption searchOption) {
        long offset = (long) searchOption.getPage() * searchOption.getSize();
        List<Exam> exams =
                queryFactory.select(QExam.exam).from(QExam.exam)
                        .leftJoin(QExam.exam.users, QUsers.users).fetchJoin()
                        .where(QExam.exam.isPublic.eq(Boolean.TRUE))
                        .where(titleSearch(searchOption.getKeyword()))
                        .orderBy(orderOption(searchOption.getSort()))
                        .offset(offset)
                        .limit(searchOption.getSize())
                        .fetch();
        List<TemplateListDto> templateListDtoList= exams.stream()
                .map(t -> new TemplateListDto(t.getTitle(), t.getUsers().getNickname(), t.getCreatedDate(),
                        t.getScrap(), t.getCnt(), t.getThumbnail(), String.valueOf(t.getRef()), t.getId())).collect(Collectors.toList());
        return templateListDtoList;
    }

    @Override
    public List<TemplateDto> getPopularTemplate() {
        List<TemplateDto> templateDtoList =
                queryFactory.select(Projections.fields(TemplateDto.class,
                                exam.title,
                                exam.createdDate,
                                exam.scrap,
                                exam.cnt,
                                exam.thumbnail,
                                exam.ref,
                                exam.id
                        )).from(exam)
                        .where(exam.isPublic.eq(true))
                        .orderBy(exam.cnt.desc())
                        .limit(5)
                        .fetch();
        return templateDtoList;
    }
    public List<MyExam> getMyExams(SearchOption searchOption, Users users) {
        long offset = (long) searchOption.getPage() * searchOption.getSize();
        List<MyExam> examList =
                queryFactory.select(Projections.fields(MyExam.class,
                                exam.title,
                                exam.createdDate,
                                exam.isPublic,
                                exam.scrap,
                                exam.cnt,
                                exam.thumbnail,
                                exam.ref,
                                exam.id
                        )).from(exam)
                        .where(exam.users.eq(users), titleSearch(searchOption.getKeyword()))
                        .orderBy(orderOption(searchOption.getSort()))
                        .offset(offset)
                        .limit(searchOption.getSize())
                        .fetch();
        return examList;
    }

    private BooleanExpression titleSearch(String keyword) {
        return StringUtils.hasText(keyword) ? exam.title.contains(keyword) : null;
    }

    private OrderSpecifier<?> orderOption(String sort) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch (sort) {
            case "popular":
                return new OrderSpecifier<Integer>(Order.DESC, exam.scrap);
            default:
                return new OrderSpecifier<Integer>(Order.DESC, exam.id);
        }
    }

    @Override
    public TemplateDetailRes getTemplateDetail(Integer id) throws MyException{
        Exam exam =
                queryFactory.select(QExam.exam).from(QExam.exam)
                        .leftJoin(QExam.exam.problems, QProblem.problem).fetchJoin()
                        .leftJoin(QExam.exam.users, QUsers.users).fetchJoin()
                        .where(QExam.exam.id.eq(id)).fetchOne();

        if (!exam.getIsPublic()) {
            throw new MyException("잘못된 접근입니다 : 비공개 문제집에 접근");
        }

        List<ProblemTemplate> list = exam.getProblems().stream()
                .map(tp -> new ProblemTemplate
                        (tp.getQuestion(), tp.getAnswer(), tp.getExImg(), tp.getExText(), tp.getIsObjective(), tp.getOpt()))
                .collect(Collectors.toList());
        return new TemplateDetailRes(exam.getTitle(), exam.getThumbnail(), exam.getCreatedDate(), exam.getCnt(),
                exam.getScrap(), exam.getUsers().getNickname(), String.valueOf(exam.getRef()), list);
    }

    @Override
    public ExamRes getExam(Integer id) {
        Exam exam =
                queryFactory.select(QExam.exam).from(QExam.exam)
                        .leftJoin(QExam.exam.problems, QProblem.problem).fetchJoin()
                        .leftJoin(QExam.exam.users, QUsers.users).fetchJoin()
                        .where(QExam.exam.id.eq(id)).fetchOne();
        List<ProblemsDto> list = exam.getProblems().stream()
                .map(p -> new ProblemsDto(p.getId(), p.getQuestion(), p.getExImg(), p.getExText(), p.getIsObjective(), p.getOpt()))
                .collect(Collectors.toList());
        return new ExamRes(new ExamDto(exam.getTitle(), list));
    }

    @Override
    public ExamStatsRes getStats(Integer id, Integer usersId) throws MyException{
        Exam exam =
        queryFactory.select(QExam.exam).from(QExam.exam)
                .leftJoin(QExam.exam.problems, QProblem.problem).fetchJoin()
                .leftJoin(QExam.exam.users, QUsers.users).fetchJoin()
                .where(QExam.exam.id.eq(id)).fetchOne();
        if (exam.getUsers().getId() != usersId) {
            throw new MyException("잘못된 접근입니다 : 다른 사용자의 문제집에 접근");
        }

        List<ProblemStatsDto> list = exam.getProblems().stream()
                .map(p -> new ProblemStatsDto(p.getQuestion(), p.getAnswer(), p.getExImg(), p.getExText(), p.getIsObjective(), p.getOpt(), p.getCnt()))
                .collect(Collectors.toList());

        return new ExamStatsRes(exam.getTitle(), exam.getThumbnail(), exam.getCreatedDate(), exam.getIsPublic(), exam.getCnt(), exam.getScrap(), exam.getUsers().getNickname(),
                exam.getRef(), list);
    }

    @Override
    public ExamResultRes getResult(Integer id, Integer usersId) throws MyException{
        List<ProblemResultTempDto> temp = queryFactory.select(Projections.fields(ProblemResultTempDto.class,
                        QProblem.problem.id,
                        QProblem.problem.question,
                        QProblem.problem.exImg,
                        QProblem.problem.exText,
                        QProblem.problem.isObjective,
                        QProblem.problem.opt,
                        QProblem.problem.answer,
                        QSubmit.submit.result,
                        QSubmit.submit.isCorrect
                )).from(QSubmit.submit)
                .join(QSubmit.submit.problem, QProblem.problem)
                .where(QProblem.problem.exam.id.eq(id).and(QSubmit.submit.users.id.eq(usersId)))
                .orderBy(QProblem.problem.id.asc())
                .fetch();

        Integer problemCnt = temp.size();
        Integer correct = 0;

        List<ProblemResultDto> problems = new ArrayList<>();
        for (ProblemResultTempDto ptd : temp) {
            problems.add(new ProblemResultDto(String.valueOf(ptd.getId()), ptd.getQuestion(), ptd.getAnswer(), ptd.getExImg(),
                    ptd.getExText(), ptd.getIsObjective(), ptd.getOpt(), ptd.getSubmitAnswer()));
            if (ptd.getIsCorrect()) correct++;
        }
        Exam exam =
                queryFactory.select(QExam.exam).from(QExam.exam)
                        .leftJoin(QExam.exam.problems, QProblem.problem).fetchJoin()
                        .leftJoin(QExam.exam.users, QUsers.users).fetchJoin()
                        .where(QExam.exam.id.eq(id)).fetchOne();

        List<SubmitResultDto> submitResultDtoList = queryFactory.select(Projections.fields(SubmitResultDto.class,
                        QSubmit.submit.users, QSubmit.submit.count().as("acc")))
                .from(QSubmit.submit)
                .where(QSubmit.submit.problem.in(exam.getProblems()).and(QSubmit.submit.isCorrect.eq(Boolean.TRUE)))
                .groupBy(QSubmit.submit.users)
                .orderBy(QSubmit.submit.count().desc())
                .fetch();

        Integer total = submitResultDtoList.size();
        int[] ranks = new int[submitResultDtoList.size()];
        long prevCorrect = submitResultDtoList.get(0).getAcc();
        int prevPeople = 1;
        int rank = 1;
        ranks[0] = rank;
        for (int i = 1; i < total; i++) {
            if (prevCorrect == submitResultDtoList.get(i).getAcc()) {
                prevPeople++;
            } else {
                rank += prevPeople;
                prevCorrect = submitResultDtoList.get(i).getAcc();
                prevPeople = 1;
            }
            ranks[i] = rank;
        }
        for (int i = 0; i < total; i++) {
            if (submitResultDtoList.get(i).getUsers().getId() == usersId) {
                rank = ranks[i];
            }
        }
        Double percentile = (double) rank / total;
        return new ExamResultRes(new ExamDto(exam.getTitle(), problems), new ResultDto(problemCnt, correct, percentile, total == 1));
    }
}
