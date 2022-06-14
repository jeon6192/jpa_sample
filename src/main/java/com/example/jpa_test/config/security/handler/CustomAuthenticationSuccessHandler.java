package com.example.jpa_test.config.security.handler;

import com.example.jpa_test.config.security.CustomUserDetails;
import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.repository.MemberRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private MemberRepositoryImpl memberRepositoryImpl;
    private final String TARGET_URL = "/me";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        setDefaultTargetUrl(TARGET_URL);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = customUserDetails.getMember();

        // 계정 잠금상태 확인
        LocalDateTime lockedDate = member.getLockedDate();
        if (lockedDate != null && lockedDate.isBefore(LocalDateTime.now())) {
            throw new LockedException("LOCKED USER");
        }

        member.resetAuthenticationInfo();

        memberRepositoryImpl.updateAuthenticationInfo(member);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
