package com.fastcampus.snsproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "userName 중복 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User 가 존재 하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "password 가 유효 하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token 이 유효 하지 않습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurs in database"),
    ALREADY_LIKED(HttpStatus.CONFLICT, "User already liked the post"),
    ALARM_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connecting alarm occurs error");


    private HttpStatus status;
    private String message;
}
