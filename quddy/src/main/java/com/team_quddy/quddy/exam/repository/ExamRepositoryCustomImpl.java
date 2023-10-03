package com.team_quddy.quddy.exam.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.exam.domain.QExam;
import com.team_quddy.quddy.exam.domain.dto.TemplateDto;
import com.team_quddy.quddy.exam.domain.dto.TemplateListDto;
import com.team_quddy.quddy.exam.domain.dto.TemplatePopularDto;
import com.team_quddy.quddy.exam.domain.response.ExamResultRes;
import com.team_quddy.quddy.exam.domain.response.MyExam;
import com.team_quddy.quddy.exam.domain.response.TemplateDetailRes;
import com.team_quddy.quddy.global.search.SearchOption;
import com.team_quddy.quddy.problem.domain.QProblem;
import com.team_quddy.quddy.problem.domain.dto.ProblemResultDto;
import com.team_quddy.quddy.problem.domain.dto.ProblemTemplate;
import com.team_quddy.quddy.submit.domain.QSubmit;
import com.team_quddy.quddy.user.domain.QUsers;
import com.team_quddy.quddy.user.domain.Users;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.team_quddy.quddy.exam.domain.QExam.exam;
import static com.team_quddy.quddy.problem.domain.QProblem.problem;
import static com.team_quddy.quddy.submit.domain.QSubmit.submit;



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
                return new OrderSpecifier<Integer>(Order.DESC, exam.cnt);
            default:
                return new OrderSpecifier<String>(Order.DESC, exam.createdDate);
        }
    }

    @Override
    public TemplateDetailRes getTemplateDetail(Integer id) {
        Exam exam =
                queryFactory.select(QExam.exam).from(QExam.exam)
                        .leftJoin(QExam.exam.problems, QProblem.problem).fetchJoin()
                        .leftJoin(QExam.exam.users, QUsers.users).fetchJoin()
                        .where(QExam.exam.id.eq(id)).fetchOne();
        List<ProblemTemplate> list = exam.getProblems().stream()
                .map(tp -> new ProblemTemplate
                        (tp.getQuestion(), tp.getAnswer(), tp.getExImg(), tp.getExText(), tp.getIsObjective(), tp.getOpt()))
                .collect(Collectors.toList());
        return new TemplateDetailRes(exam.getTitle(), exam.getThumbnail(), exam.getCreatedDate(), exam.getCnt(),
                exam.getScrap(), exam.getUsers().getNickname(), String.valueOf(exam.getRef()), list);
    }

    @Override
    public ExamResultRes getResult(Integer id, Integer usersId) {
        Exam exam =
                queryFactory.select(QExam.exam).from(QExam.exam)
                        .leftJoin(QExam.exam.problems, QProblem.problem).fetchJoin()
                        .leftJoin(QExam.exam.users, QUsers.users).fetchJoin()
                        .where(QExam.exam.id.eq(id)).fetchOne();

        List<ProblemResultDto> list = exam.getProblems().stream()
                .map(p -> new ProblemResultDto(p.getQuestion(), p.getAnswer(), p.getExImg(), p.getExText(), p.getIsObjective(), p.getOpt(), p.getCnt()))
                .collect(Collectors.toList());

        return new ExamResultRes(exam.getTitle(), exam.getThumbnail(), exam.getCreatedDate(), exam.getCnt(), exam.getScrap(), exam.getUsers().getNickname(),
                exam.getRef(), list);
    }
}
