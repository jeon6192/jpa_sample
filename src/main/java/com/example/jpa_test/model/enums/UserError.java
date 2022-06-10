package com.example.jpa_test.model.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum UserError {
    BAB_REQUEST("001", "요청 값 오류", BAD_REQUEST),
    PERMISSION_DENIED("002", "인증되지 않음", UNAUTHORIZED),
    HAVE_NO_DATA("003", "데이터 없음", BAD_REQUEST),
    FORBIDDEN("004", "권한 없음", HttpStatus.FORBIDDEN)
    ;

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    UserError(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
