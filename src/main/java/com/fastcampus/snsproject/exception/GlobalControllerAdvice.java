package com.fastcampus.snsproject.exception;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fastcampus.snsproject.controller.response.Response;

import lombok.*:

@Slf4j
@RestControllerAdvice;
public class GlobalControllerAdvice {

	@ExceptionHandler(SnsApplicationException.class)
	public ResponseEntity<?> applicationHandler(SnsApplicationException e){
		log.error("Error occurs {}", e.toString());
		return ResponseEntity.status(e.getErrorCode().getStatus())
				.body(Response.error(e.getErrorCode().name()));
	}
}
