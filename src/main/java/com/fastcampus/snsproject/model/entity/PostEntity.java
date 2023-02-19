package com.fastcampus.snsproject.model.entity;

import com.fastcampus.snsproject.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name ="\"post\"")
@SQLDelete(sql = "UPDATE \"post\" SET deleted_at = NOW() where id =?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "title" )
    private String title;
    @Column(name = "body", columnDefinition = "TEXY")
    private String body;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "registered_at")
    private Timestamp registeredAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updateAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


    // UserEntity를 담아가는 메서드
    public static PostEntity of(String title, String body, UserEntity userEntity){
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(title);
        postEntity.setBody(body);
        postEntity.setUser(userEntity);
        return postEntity;
    }
}
