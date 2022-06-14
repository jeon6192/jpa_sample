package com.example.jpa_test.config.security.guard;

import com.example.jpa_test.config.security.CustomUserDetails;
import com.example.jpa_test.model.entity.Role;
import com.example.jpa_test.model.enums.UserAuthority;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuardHelper {

    // CustomUserDetails 객체 내의 필드인 member entity 의 idx 를 반환
    public static Long extractMemberIdx() {
        return getUserDetails().getMember().getIdx();
    }

    // CustomUserDetails 객체 내의 필드인 member entity 의 ROLE 목록을 가져와 Set 으로 반환
    public static Set<UserAuthority> extractMemberAuthorities() {
        return getUserDetails().getMember().getRoles()
                .stream()
                .map(Role::getUserAuthority)
                .collect(Collectors.toSet());
    }

    // 시큐리티 인증정보를 CustomUserDetails 객체로 변환
    private static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }

    // ContextHolder 에서 현재 로그인 된 인증정보를 얻어 옴
    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
