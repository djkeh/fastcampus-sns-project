package com.fastcampus.snsproject.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"like\"")
@Getter
@Setter
@SQLDelete(sql = "UPDATE \"like\" SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class LikeEntiity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "register_at")
    private Timestamp registerAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;


    @PrePersist
    void registedAt(){
        this.registerAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updateAt(){
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static LikeEntiity of(UserEntity userEntity, PostEntity postEntity){
        LikeEntiity likeEntiity = new LikeEntiity();
        likeEntiity.setUser(userEntity);
        likeEntiity.setPost(postEntity);

        return likeEntiity;
    }
}
