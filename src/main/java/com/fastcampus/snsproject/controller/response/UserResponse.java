package com.fastcampus.snsproject.controller.response;

public class UserResponse {
	private Integer id;
	private String userName;
	private UserRole user;
	
	public static UserResponse fromUser(User user) {
		return new UserResponse(
				user.getId();
				user.getUsername();
				user.getUserRole();
				)
	}
}
