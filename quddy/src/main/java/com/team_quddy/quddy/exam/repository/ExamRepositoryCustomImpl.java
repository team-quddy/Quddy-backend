package com.team_quddy.quddy.exam.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.exam.domain.QExam;
import com.team_quddy.quddy.exam.domain.dto.TemplateDto;
import com.team_quddy.quddy.exam.domain.response.MyExam;
import com.team_quddy.quddy.exam.domain.response.TemplateDetailRes;
import com.team_quddy.quddy.global.search.SearchOption;
import com.team_quddy.quddy.problem.domain.QProblem;
import com.team_quddy.quddy.problem.domain.dto.ProblemTemplate;
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


@Slf4j
public class ExamRepositoryCustomImpl implements ExamRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ExamRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
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
                        .fetch();
        return examList;
    }

    private BooleanExpression titleSearch(String keyword) {
        return StringUtils.hasText(keyword) ? exam.title.contains(keyword) : null;
    }

    private OrderSpecifier<?> orderOption(String sort) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch (sort) {
            case "latest":
                return new OrderSpecifier<String>(Order.DESC, exam.createdDate);
            case "popular":
                return new OrderSpecifier<Integer>(Order.DESC, exam.ref);
            default:
                return null;
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
}
