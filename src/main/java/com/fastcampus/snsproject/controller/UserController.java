package com.fastcampus.snsproject.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.snsproject.controller.request.UserJoinRequest;
import com.fastcampus.snsproject.controller.response.Response;
import com.fastcampus.snsproject.controller.response.UserJoinResponse;
import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.service.UserService;

import lombok.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@PostMapping("/join")
	public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
		
		User user = userService.join(request.getUserName(), request.getPassword());
		//UserJoinResponse response = UserJoinResponse.fromUser(user);
		return Response.success(UserJoinResponse.fromUser(user));
	}


}
