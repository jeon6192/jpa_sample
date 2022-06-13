package com.example.jpa_test.model.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum UserError {
    BAD_REQUEST("001", "요청 값 오류", HttpStatus.BAD_REQUEST),
    PERMISSION_DENIED("002", "인증되지 않음", UNAUTHORIZED),
    HAVE_NO_DATA("003", "데이터 없음", NO_CONTENT),
    FORBIDDEN("004", "권한 없음", HttpStatus.FORBIDDEN),
    NOT_DEFINED("999", "서버 에러", INTERNAL_SERVER_ERROR)
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
