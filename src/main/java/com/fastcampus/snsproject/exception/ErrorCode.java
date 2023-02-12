package com.fastcampus.snsproject.exception;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"), 
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "not found");
	

	private HttpStatus status;
	private String message;
}
