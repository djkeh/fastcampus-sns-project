package com.fastcampus.snsproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User is not found"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "token is invalid."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),
    ALREADY_LIKED(HttpStatus.CONFLICT, "User already liked this post"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurs in database"),
    ALARM_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connecting alarm occurs error");

    ;

    private HttpStatus status;
    private String message;
}
