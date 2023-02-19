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
@Table(name ="\"like\"")
@SQLDelete(sql = "UPDATE \"like\" SET deleted_at = NOW() where id =?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

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
    public static LikeEntity of(UserEntity userEntity, PostEntity postEntity){
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setUser(userEntity);
        likeEntity.setPost(postEntity);
        return likeEntity;
    }
}
