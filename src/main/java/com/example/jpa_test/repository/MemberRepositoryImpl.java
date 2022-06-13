package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.model.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MemberRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QMember qMember = QMember.member;

    @Transactional
    public Long updateMemberId(Member member) {
        return queryFactory
                .update(qMember)
                .set(qMember.memberId, member.getMemberId())
                .where(qMember.idx.eq(member.getIdx()))
                .execute();
    }

    @Transactional
    public Long updateMemberPassword(Member member) {
        return queryFactory
                .update(qMember)
                .set(qMember.password, member.getPassword())
                .where(qMember.idx.eq(member.getIdx()))
                .execute();
    }

    @Transactional
    public Long updateMemberInfo(Member member) {
        return queryFactory
                .update(qMember)
                .set(qMember.birth, member.getBirth())
                .set(qMember.name, member.getName())
                .where(qMember.idx.eq(member.getIdx()))
                .execute();
    }
}
