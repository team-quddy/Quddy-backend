package com.team_quddy.quddy.exam.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_quddy.quddy.exam.domain.dto.TemplateDto;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import java.util.List;

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
}
