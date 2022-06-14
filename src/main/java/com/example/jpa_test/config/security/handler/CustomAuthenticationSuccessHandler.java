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
        // 로그인 성공 후 이동할 url 설정
        setDefaultTargetUrl(TARGET_URL);

        // 로그인된 유저객체 얻어오기
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = customUserDetails.getMember();

        // 계정 잠금상태 확인. 잠김시간 이전 이면 LockedException 발생시킴
        LocalDateTime lockedDate = member.getLockedDate();
        if (lockedDate != null && lockedDate.isBefore(LocalDateTime.now())) {
            throw new LockedException("LOCKED USER");
        }
        // member 테이블에 여러 인증정보를 추가하면 추가 로직 필요 ex) 계정 만료, 비밀번호 만료 등

        // 로그인에 성공 시 인증정보 초기화
        member.resetAuthenticationInfo();
        memberRepositoryImpl.updateAuthenticationInfo(member);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
