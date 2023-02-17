package com.fastcampus.sns.model;

import com.fastcampus.sns.model.entity.PostEntity;
import com.fastcampus.sns.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Post {
    private Integer id;

    private String title;

    private String body;

    private User user;

    private Timestamp registerdAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    public static Post fromEntity(PostEntity entity){
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                User.fromEntity(entity.getUser()),
                entity.getRegisterdAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()

        );
    }
}
