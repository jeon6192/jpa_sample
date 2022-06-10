package com.example.jpa_test.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleResponse {
    private boolean result;
    private String message;

    @Builder
    public SimpleResponse(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public static SimpleResponse createSuccessResponse() {
        return SimpleResponse.builder()
                .result(true)
                .message("success")
                .build();
    }
}
