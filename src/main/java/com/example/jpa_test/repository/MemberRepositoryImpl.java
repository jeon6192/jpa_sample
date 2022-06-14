package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.model.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// Querydsl 사용을 위한 member repository 생성
/*
    MemberRepository extends JpaRepository
    MemberRepositoryCustom extends MemberRepository
    MemberRepositoryImpl (Querydsl) implements MemberRepositoryCustom

    위의 방법을 사용 할 수 있지만 JPAQueryFactory 를 Bean 으로 생성 후 @Repository 어노테이션을 통해 사용하는 방법을 택함
 */
@Repository
public class MemberRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QMember qMember = QMember.member;

    @Transactional
    public void updateMemberId(Member member) {
        queryFactory
                .update(qMember)
                .set(qMember.memberId, member.getMemberId())
                .where(qMember.idx.eq(member.getIdx()))
                .execute();
    }

    @Transactional
    public void updateMemberPassword(Member member) {
        queryFactory
                .update(qMember)
                .set(qMember.password, member.getPassword())
                .where(qMember.idx.eq(member.getIdx()))
                .execute();
    }

    @Transactional
    public void updateMemberInfo(Member member) {
        queryFactory
                .update(qMember)
                .set(qMember.birth, member.getBirth())
                .set(qMember.name, member.getName())
                .where(qMember.idx.eq(member.getIdx()))
                .execute();
    }

    @Transactional
    public void updateAuthenticationInfo(Member member) {
        queryFactory.update(qMember)
                .set(qMember.passwordFailCount, member.getPasswordFailCount())
                .set(qMember.isLocked, member.getIsLocked())
                .set(qMember.lockedDate, member.getLockedDate())
                .execute();
    }
}
