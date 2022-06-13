package com.example.jpa_test.exception;

import com.example.jpa_test.model.dto.ErrorResponse;
import com.example.jpa_test.model.enums.UserError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        e.printStackTrace();

        final UserError userError = e.getUserError();

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(userError.getErrorCode())
                .message(userError.getMessage())
                .detail(e.getDetail())
                .build();

        return new ResponseEntity<>(errorResponse, userError.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        e.printStackTrace();

        final UserError userError = UserError.FORBIDDEN;

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(userError.getErrorCode())
                .message(userError.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        e.printStackTrace();

        final UserError userError = UserError.NOT_DEFINED;

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(userError.getErrorCode())
                .message(userError.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, userError.getHttpStatus());
    }
}
