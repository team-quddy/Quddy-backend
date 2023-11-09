package com.team_quddy.quddy.submit.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_quddy.quddy.exam.domain.QExam;
import com.team_quddy.quddy.exam.domain.response.SolveExam;
import com.team_quddy.quddy.problem.domain.QProblem;
import com.team_quddy.quddy.submit.domain.Submit;
import com.team_quddy.quddy.user.domain.Users;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import java.util.*;

import static com.team_quddy.quddy.submit.domain.QSubmit.*;

@Service
public class SubmitRepositoryCustomImpl implements SubmitRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public SubmitRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Boolean getSubmit(Integer examId, Integer usersId) {
        List<Submit> submitList = queryFactory.select(submit)
                .from(submit)
                .where(submit.problem.exam.id.eq(examId)
                        .and(submit.users.id.eq(usersId)))
                .fetch();
        return submitList.size() > 0;
    }

    @Override
    public List<SolveExam> getSolveMyExams(Users users) {
        List<SolveExam> solveExamList = queryFactory.select(Projections.fields(SolveExam.class,
                        submit.problem.exam.id,
                        submit.problem.exam.title,
                        submit.problem.exam.thumbnail,
                        submit.createdDate.as("date"))).from(submit)
                .leftJoin(submit.problem, QProblem.problem)
                .leftJoin(submit.problem.exam, QExam.exam)
                .where(submit.users.eq(users))
                .fetch();
        HashSet<SolveExam> set = new HashSet<>();
        for (SolveExam se : solveExamList) {
            set.add(se);
        }
        solveExamList = new ArrayList<>(set);
        Collections.sort(solveExamList);
        Collections.reverse(solveExamList);
        return solveExamList;
    }
}