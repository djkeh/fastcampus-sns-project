package com.fastcampus.snsproject.exception;

import lombok.*;

@AllArgsConstructor
public class SnsApplicationException extends RuntimeException{
	
	private ErrorCode errorCode;
	private String message;
	

	@Override
	public String getMessage() {
		if(message == null) {
			return errorCode,getMessage();
		}
		return String.format("%s. %s", errorCode, getMessage(), message);
	}
}
