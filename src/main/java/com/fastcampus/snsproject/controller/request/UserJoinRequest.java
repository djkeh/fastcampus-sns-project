package com.fastcampus.snsproject.controller.request;

import lombok.AllArgConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequest {
	
	private String userName;
	private String password;
	
	
}
