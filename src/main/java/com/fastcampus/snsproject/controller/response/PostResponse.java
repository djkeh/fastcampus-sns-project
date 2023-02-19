package com.fastcampus.snsproject.controller.response;

import java.sql.Timestamp;

import com.fastcampus.snsproject.model.Post;
import com.fastcampus.snsproject.model.User;
import lombok.*;

@Getter
@AllArgsConstructor
public class PostResponse {
	private String title;
	private String body;
	private UserResponse user;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;
	
	public static PostResponse fromPost(Post post) {
		return new PostResponse(
				post.getId(),
				post.getTitle(),
				post.getBody(),
				UserResponse.fromUser(post.getUser()),
				post.getRegisteredAt(),
				post.getUpdatedAt(),
				post.getDeletedAt()
				);
				
	}
}
