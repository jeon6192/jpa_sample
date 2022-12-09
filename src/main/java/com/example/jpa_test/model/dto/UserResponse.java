package com.example.jpa_test.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private boolean result;
    private String message;
    private Map<String, Object> details;

    @Builder
    public UserResponse(boolean result, String message, Map<String, Object> details) {
        this.result = result;
        this.message = message;
        this.details = details;
    }

    public static UserResponse createSuccessResponse() {
        return UserResponse.builder()
                .result(true)
                .message("success")
                .details(Collections.emptyMap())
                .build();
    }

    public static UserResponse createSuccessResponse(String message, Map<String, Object> details) {
        return UserResponse.builder()
                .result(true)
                .message(message)
                .details(details)
                .build();
    }
}
