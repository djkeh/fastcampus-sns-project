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
@Table(name = "\"post\"")
@Getter
@Setter
@SQLDelete(sql = "UPDATED \"post\" SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class PostEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name= "body", columnDefinition = "TEXT")
	private String body;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
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
	

	public static PostEntity of(String title, String body, UserEntity userEntity){
		PostEntity entity = new PostEntity();
		entity.setTitle(title);
		entity.setBody(body);
		entity.setUser(userEntity);
		return entity;
	}
}
