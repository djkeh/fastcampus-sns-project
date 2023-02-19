package com.fastcampus.snsproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.transaction.Transaction;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error"),
    ALREADY_LIKED(HttpStatus.CONFLICT, "User already liked the post"),
    ;

    private HttpStatus status;
    private String message;
}
