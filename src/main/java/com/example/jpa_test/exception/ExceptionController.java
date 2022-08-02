package com.example.jpa_test.exception;

import com.example.jpa_test.model.dto.ErrorResponse;
import com.example.jpa_test.model.enums.UserError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        e.printStackTrace();

        final UserError userError = e.getUserError();

        Map<String, Object> details = new HashMap<>();
        details.put("detail", e.getDetail());

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(userError.getErrorCode())
                .message(userError.getMessage())
                .details(details)
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

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    protected ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        e.printStackTrace();

        BindingResult bindingResult = e.getBindingResult();
        Map<String, Object> details = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<Map<String, Object>> errors = new ArrayList<>();

            for (FieldError fieldError : fieldErrors) {
                Map<String, Object> error = new HashMap<>();
                error.put("field", fieldError.getField());
                error.put("rejectedValue", fieldError.getRejectedValue());
                error.put("message", fieldError.getDefaultMessage());

                errors.add(error);
            }

            details.put("erros", errors);
        }

        final UserError userError = UserError.BAD_REQUEST;

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(userError.getErrorCode())
                .message(userError.getMessage())
                .details(details)
                .build();

        return new ResponseEntity<>(errorResponse, userError.getHttpStatus());
    }
}
