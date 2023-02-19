package com.fastcampus.snsproject.model;

import java.sql.Timestamp;
import lombok.*;

//import org.springframework.security.core.userdetails

import com.fastcampus.snsproject.model.entity.UserEntity;

@Getter
@AllArgsConstructor;
public class User {
	private Integer id;
	private String userName;
	private String password;
	private UserRole userRole;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;
	
	public static User fromEntity(UserEntity entity) {
		return new User(
				entity.getId(),
				entity.getUserName(),
				entity.getPassword(),
				entity.getUserRole(),
				entity.getRegisteredAt(),
				entity.getUpdatedAt(),
				entity.getDeletedAt()
				);
	}
	
	
}
