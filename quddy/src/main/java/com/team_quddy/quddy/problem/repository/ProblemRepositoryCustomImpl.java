package com.team_quddy.quddy.problem.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.problem.domain.QProblem;
import com.team_quddy.quddy.problem.domain.dto.ProblemTemplate;

import javax.persistence.EntityManager;
import java.util.List;

import static com.team_quddy.quddy.problem.domain.QProblem.problem;

public class ProblemRepositoryCustomImpl implements ProblemRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProblemRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProblemTemplate> getProblemTemplate(Exam exam) {
        List<ProblemTemplate> problemTemplateList =
                queryFactory.select(Projections.fields(ProblemTemplate.class,
                                problem.question,
                                problem.answer,
                                problem.exImg,
                                problem.exText,
                                problem.isObjective,
                                problem.opt
                        )).from(problem)
                        .where(problem.exam.eq(exam))
                        .fetch();
        return problemTemplateList;
    }
}
