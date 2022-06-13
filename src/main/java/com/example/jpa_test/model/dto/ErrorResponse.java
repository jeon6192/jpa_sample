package com.example.jpa_test.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String errorCode;
    private String message;
    private String detail;

    @Builder
    public ErrorResponse(String errorCode, String message, String detail) {
        this.errorCode = errorCode;
        this.message = message;
        this.detail = detail;
    }
}
