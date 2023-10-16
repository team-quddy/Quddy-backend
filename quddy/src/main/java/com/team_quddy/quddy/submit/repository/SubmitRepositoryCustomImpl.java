package com.team_quddy.quddy.submit.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_quddy.quddy.submit.domain.QSubmit;
import com.team_quddy.quddy.submit.domain.Submit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import java.util.List;

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
}
