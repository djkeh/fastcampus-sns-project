package com.fastcampus.snsproject.model.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity

import com.fastcampus.snsproject.model.UserRole;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@SQLDelete(sql = "UPDATED \"user\" SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name= "password")
	private String password;
	
	@Column(name= "role")
	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.USER;
	
	@Column(name= "registered_at")
	private Timestamp registeredAt;
	
	@Column(name= "updated_at")
	private Timestamp updatedAt;
	
	@Column(name= "deleted_at")
	private Timestamp deletedAt;
	
	@PrePersist
	void registeredAt() {
		this.registeredAt = Timestamp.from(Instant.now());
	}
	
	@PreUpdate
	void updatedAt() {
		this.updatedAt = Timestamp.from(Instant.now());
	}
	
	public static UserEntity of(String userName, String password) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(userName);
		userEntity.setPassword(password);
		return userEntity;
	}

}
