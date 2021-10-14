package com.company.customeridentificationsystem.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.List;

import static java.util.Collections.singletonList;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CallError<String>> handleValidationException(HttpServletRequest request, ValidationException ex) {
        log.error("ValidationException {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CallError<>("Validation exception", singletonList(ex.getMessage())));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CallError<T> {
        private String message;
        private List<T> list;
    }

}
