package com.fastcampus.snsproject.exception;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"), 
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not found"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "password invalid"),
	POST_NOT_FOUNT(HttpStatus.NOT_FOUND, "post not found"),
	INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "invalid permission"),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "permission invalid");
	
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error");
	
	private HttpStatus status;
	private String message;
}
