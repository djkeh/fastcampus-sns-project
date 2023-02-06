package com.fastcampus.snsproject.excpetion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicated user name"),
    USER_NO_FOUND(HttpStatus.NOT_FOUND, "User not Founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not Founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid"),


    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),

    ALREADY_LIKED(HttpStatus.CONFLICT, "User already liked the post");

    private final HttpStatus status;
    private final String message;


}
