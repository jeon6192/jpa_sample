package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.model.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QMember qMember = QMember.member;

    @Override
    public void updateMemberId(Member member) {
        queryFactory.update(qMember)
                .set(qMember.memberId, member.getMemberId())
                .where(qMember.idx.eq(member.getIdx()))
                .execute();
    }

    @Override
    public void updateMemberPassword(Member member) {
        queryFactory.update(qMember)
                .set(qMember.password, member.getPassword())
                .where(qMember.idx.eq(member.getIdx()))
                .execute();
    }

    @Override
    public void updateMemberInfo(Member member) {
        queryFactory.update(qMember)
                .set(qMember.birth, member.getBirth())
                .set(qMember.name, member.getName())
                .set(qMember.phone, member.getPhone())
                .where(qMember.idx.eq(member.getIdx()))
                .execute();
    }

    @Override
    public void updateAuthenticationInfo(Member member) {
        queryFactory.update(qMember)
                .set(qMember.passwordFailCount, member.getPasswordFailCount())
                .set(qMember.isLocked, member.getIsLocked())
                .set(qMember.lockedDate, member.getLockedDate())
                .execute();
    }
}
