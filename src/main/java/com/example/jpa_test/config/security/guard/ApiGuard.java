package com.example.jpa_test.config.security.guard;

import com.example.jpa_test.exception.UserException;
import com.example.jpa_test.model.enums.UserAuthority;
import com.example.jpa_test.model.enums.UserError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("apiGuard")
public class ApiGuard {

    // 인증에 통과시킬 ROLE 종류를 세팅
    private final List<UserAuthority> userAuthorities = List.of(UserAuthority.ROLE_ADMIN);

    public final boolean checkAuthority(Long idx) throws UserException {
        if (idx == null) {
            throw new UserException(UserError.BAD_REQUEST);
        }

        return hasRole(getUserAuthorities()) || isOwner(idx);
    }

    // 파라미터로 받은 idx 와 현재 로그인된 유저의 idx 를 비교
    private boolean isOwner(Long idx) {
        return Objects.equals(idx, GuardHelper.extractMemberIdx());
    }

    private List<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }

    // userAuthorities 에 정해준 ROLE 목록과 현재 로그인 된 유저의 ROLE 목록을 비교함
    private boolean hasRole(List<?> userAuthorities) {
        return GuardHelper.extractMemberAuthorities().containsAll(userAuthorities);
    }
}
