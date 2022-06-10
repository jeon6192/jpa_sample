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

    public static Long extractMemberIdx() {
        return getUserDetails().getMember().getIdx();
    }

    public static Set<UserAuthority> extractMemberAuthorities() {
        return getUserDetails().getMember().getRoles()
                .stream()
                .map(Role::getUserAuthority)
                .collect(Collectors.toSet());
    }

    private static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
