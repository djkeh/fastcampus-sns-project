package com.fastcampus.sns.exception;

import com.fastcampus.sns.controller.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.fastcampus.sns.exception.ErrorCode.DATABASE_ERROR;
import static com.fastcampus.sns.exception.ErrorCode.NOTIFICATION_CONNECT_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(SnsApplicationException.class)
    public ResponseEntity<?> errorHandler(SnsApplicationException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().name()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> databaseErrorHandler(IllegalArgumentException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(DATABASE_ERROR.getStatus())
                .body(Response.error(DATABASE_ERROR.name()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> databaseErrorHandler(RuntimeException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(NOTIFICATION_CONNECT_ERROR.getStatus())
                .body(Response.error(NOTIFICATION_CONNECT_ERROR.name()));
    }
}