package com.example.jpa_test.config.security.guard;

import com.example.jpa_test.exception.UserException;
import com.example.jpa_test.model.enums.UserAuthority;
import com.example.jpa_test.model.enums.UserError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("apiGuard")
public class ApiGuard {

    private final List<UserAuthority> userAuthorities = List.of(UserAuthority.ROLE_ADMIN);

    public final boolean checkAuthority(Long idx) throws UserException {
        if (idx == null) {
            throw new UserException(UserError.BAB_REQUEST);
        }

        return hasRole(getUserAuthorities()) || isOwner(idx);
    }

    private boolean isOwner(Long idx) {
        return Objects.equals(idx, GuardHelper.extractMemberIdx());
    }

    private List<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }

    private boolean hasRole(List<?> userAuthorities) {
        return GuardHelper.extractMemberAuthorities().containsAll(userAuthorities);
    }
}
