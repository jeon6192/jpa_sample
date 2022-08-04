package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.Member;

public interface MemberRepositoryCustom {

    void updateMemberId(Member member);

    void updateMemberPassword(Member member);

    void updateMemberInfo(Member member);

    void updateAuthenticationInfo(Member member);
}
