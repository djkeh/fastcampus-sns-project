package com.fastcampus.snsproject.model.entity;

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
@Table(name ="\"comment\"", indexes = {
        @Index(name = "post_id_idx", columnList = "post_id")
})
@SQLDelete(sql = "UPDATE \"comment\" SET deleted_at = NOW() where id =?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name="comment")
    private String comment;

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


    // LikeEntity를 담아가는 메서드
    public static CommentEntity of(UserEntity userEntity, PostEntity postEntity, String comment){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUser(userEntity);
        commentEntity.setPost(postEntity);
        commentEntity.setComment(comment);
        return commentEntity;
    }
}
