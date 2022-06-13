package com.example.jpa_test.exception;

import com.example.jpa_test.model.enums.UserError;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserException extends Exception {
    private UserError userError;
    private String detail;

    public UserException(UserError userError) {
        this.userError = userError;
    }

    public UserException(UserError userError, String detail) {
        this.userError = userError;
        this.detail = detail;
    }
}
