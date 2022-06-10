package com.example.jpa_test.exception;

import com.example.jpa_test.model.enums.UserError;
import lombok.Getter;

@Getter
public class UserException extends Exception {
    private UserError userError;

    public UserException(UserError userError) {
        this.userError = userError;
    }
}
