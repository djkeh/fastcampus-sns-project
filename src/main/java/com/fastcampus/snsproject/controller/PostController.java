package com.fastcampus.snsproject.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.*;
import org.springframework.security.core.Authentication;

import com.fastcampus.snsproject.controller.request.PostCreateRequest;
import com.fastcampus.snsproject.controller.request.UserJoinRequest;
import com.fastcampus.snsproject.controller.response.PostResponse;
import com.fastcampus.snsproject.controller.response.Response;
import com.fastcampus.snsproject.model.Post;
import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.service.PostService;


import lombok.*;


@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	
	@PostMapping
	public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication){
		postService.create(request.getTitle(), request.getBody(), authentication.getName());
		return Response.success(null);
	}
	
	@PostMapping("/{postId}")
	public Response<PostResponse> modify(@PathVariable Integer postId, @RequestBody PostModifyRequest request, Authentication authentication){
		Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postId);
		return Response.success(PostResponse.fromPost(post));
	}
	
	@DeleteMapping("/{postId}")
	public Response<Void> delete(@PathVariable Integer postId, Authentication authentication){
		postService.delete(authentication.getName(), postId);
		return Response.success(null);

	}
}
