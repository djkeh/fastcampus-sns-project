package com.fastcampus.snsproject.model;

import java.sql.Timestamp;

import com.fastcampus.snsproject.model.entity.Column;
import com.fastcampus.snsproject.model.entity.GeneratedValue;
import com.fastcampus.snsproject.model.entity.Id;
import com.fastcampus.snsproject.model.entity.JoinColumn;
import com.fastcampus.snsproject.model.entity.ManyToOne;
import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import lombok.*;


@Getter
@AllArgsConstructor;
public class Post {
	private Integer id;
	private String title;
	private String body;
	private User user;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;
	
	public static Post fromEntity(PostEntity entity) {
		return new Post(
				entity.getId(),
				entity.getTitle(),
				entity.getBody(),
				entity.getUser(),
				entity.getRegisteredAt(),
				entity.getUpdatedAt(),
				entity.getDeletedAt()
				);
	}
}
