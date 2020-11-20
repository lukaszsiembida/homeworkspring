package com.lsiembida.homeworkspring.api;

import com.lsiembida.homeworkspring.exception.AlreadyExistException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.UUID;

@ControllerAdvice
public class GlobalErrorHandler {

    @ResponseBody
    @ExceptionHandler(value = AlreadyExistException.class)
    public ResponseEntity<Error> handleAlreadyExist(AlreadyExistException ex) {
        String errorCode = UUID.randomUUID().toString();
        System.out.println("Error code " + errorCode);
        ex.printStackTrace();

        return ResponseEntity.status(409).body(new Error(ex.getMessage(), LocalDateTime.now(), errorCode));
    }

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Error> handleAnyRuntimeException(RuntimeException ex) {
        String errorCode = UUID.randomUUID().toString();
        System.out.println("Error code " + errorCode);
        ex.printStackTrace();

        return ResponseEntity.status(500).body(new Error(ex.getMessage(), LocalDateTime.now(), errorCode));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public String handleAccessDenied() {
        return "redirect:/mvc/login";
    }

    @AllArgsConstructor
    @Getter
    static class Error {
        private final String message;
        private final LocalDateTime errorTime;
        private final String errorCode;
    }
}
