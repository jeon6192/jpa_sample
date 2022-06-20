package com.example.jpa_test.config.security;

import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.repository.MemberRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {

    private final MemberRepositoryImpl memberRepositoryImpl;

    public LoginService(MemberRepositoryImpl memberRepositoryImpl) {
        this.memberRepositoryImpl = memberRepositoryImpl;
    }

    public Member getUserDetailsByAuthentication(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getMember();
    }

    public void updateAuthenticationInfo(Member member) {
        memberRepositoryImpl.updateAuthenticationInfo(member);
    }
}
