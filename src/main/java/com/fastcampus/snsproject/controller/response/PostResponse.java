package com.fastcampus.sns.controller.response;

import com.fastcampus.sns.model.Post;
import com.fastcampus.sns.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor

public class PostResponse {
    private Integer id;

    private String title;

    private String body;

    private UserResponse user;

    private Timestamp registerdAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    public static PostResponse fromPost(Post post){

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                UserResponse.fromUser(post.getUser()),
                post.getRegisterdAt(),
                post.getUpdatedAt(),
                post.getDeletedAt()
        );

    }
}
