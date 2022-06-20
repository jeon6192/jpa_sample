package com.example.jpa_test.config.security;

import com.example.jpa_test.config.security.LoginService;
import com.example.jpa_test.model.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 후 이동할 url 설정
        String targetUrl = "/me";
        setDefaultTargetUrl(targetUrl);

        // 로그인된 유저객체 얻어오기
        Member member = loginService.getUserDetailsByAuthentication(authentication);

        // 계정 잠금상태 확인. 잠김시간 이전 이면 LockedException 발생시킴
        if (member.isBeforeLockedDate()) {
            throw new LockedException("LOCKED USER");
        }
        // member 테이블에 여러 인증정보를 추가하면 추가 로직 필요 ex) 계정 만료, 비밀번호 만료 등

        // 로그인에 성공 시 인증정보 초기화
        member.resetAuthenticationInfo();
        loginService.updateAuthenticationInfo(member);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
