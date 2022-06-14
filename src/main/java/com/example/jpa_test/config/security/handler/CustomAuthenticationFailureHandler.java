package com.example.jpa_test.config.security.handler;

import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.model.enums.UserError;
import com.example.jpa_test.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MemberRepository memberRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String memberId = request.getParameter("memberId");

        Optional<Member> memberOptional = memberRepository.findByMemberId(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // 비밀번호가 틀렸을 경우, 비밀번호 실패횟수 증가.
            if (exception instanceof BadCredentialsException) {
                int passwordFailCount = member.getPasswordFailCount() + 1;

                if (passwordFailCount > 5) {
                    long lockTimeMinutes = 30;
                    member.updateLockedInfo(true, LocalDateTime.now().plusMinutes(lockTimeMinutes));
                } else {
                    member.updatePasswordFailCount(passwordFailCount);
                }

                memberRepository.save(member);

                printErrorMessage(response, UserError.MISMATCH_PASSWORD);
                return; // json을 뿌려주기 위해 리턴.
            } else if (exception instanceof LockedException) {
                printErrorMessage(response, UserError.LOCKED_USER);
                return; // json을 뿌려주기 위해 리턴.
            } else if (exception instanceof AccountExpiredException) {
                printErrorMessage(response, UserError.EXPIRED_USER);
                return; // json을 뿌려주기 위해 리턴.
            } else if (exception instanceof CredentialsExpiredException) {
                printErrorMessage(response, UserError.EXPIRED_PASSWORD);
                return; // json을 뿌려주기 위해 리턴.
            } else if (exception instanceof DisabledException) {
                printErrorMessage(response, UserError.DISABLED_USER);
                return; // json을 뿌려주기 위해 리턴.
            }

        } else {
            printErrorMessage(response, UserError.NOT_FOUND_USER);
            return; // json을 뿌려주기 위해 리턴.
        }

        super.onAuthenticationFailure(request, response, exception);
    }

    private void printErrorMessage(HttpServletResponse response, @NonNull UserError userError) throws IOException {
        int statusCode;
        if (userError.getHttpStatus() != null) {
            statusCode = userError.getHttpStatus().value();
        } else {
            statusCode = HttpStatus.UNAUTHORIZED.value();
        }

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("errorCode", userError.getErrorCode());
        errorMap.put("message", userError.getMessage());

        String errorJson = objectMapper.writeValueAsString(errorMap);
        PrintWriter writer = response.getWriter();
        writer.println(errorJson);
    }
}
