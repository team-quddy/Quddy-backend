package com.team_quddy.quddy.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.exam.domain.QExam;
import com.team_quddy.quddy.problem.domain.Problem;
import com.team_quddy.quddy.user.domain.QUsers;
import com.team_quddy.quddy.user.domain.Users;
import com.team_quddy.quddy.user.domain.response.UsersRes;

import javax.persistence.EntityManager;

import static com.team_quddy.quddy.user.domain.QUsers.users;

public class UsersRepositoryCustomImpl implements UsersRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public UsersRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public UsersRes getInfo(Integer id) {
        Users users = queryFactory.select(QUsers.users).from(QUsers.users)
                .leftJoin(QUsers.users.exams, QExam.exam).fetchJoin()
                .where(QUsers.users.id.eq(id)).fetchOne();
        Integer publicExamCnt = 0;
        Integer scrap = 0;
        for (Exam e : users.getExams()) {
            if (e.getIsPublic()) {
                publicExamCnt++;
                scrap += e.getScrap();
            }
        }
        return new UsersRes(users.getNickname(),
                String.valueOf(users.getId()),
                users.getExams().size(),
                publicExamCnt, scrap);
    }
}
