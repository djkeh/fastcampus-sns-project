package com.fastcampus.snsproject.controller.response;

import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.model.UserRole;

public class UserJoinResponse {

	private String userName;
	private String id;
	private UserRole role;
	
	public static UserJoinResponse fromUser(User user) {
		return new UserJoinResponse(
				user.getId(),
				user.getUserName(),
				user.getUserRole()
				);
				
	}
}
