package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.entity.CommentEntity;
import com.fastcampus.snsproject.model.entity.LikeEntity;
import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface CommentEntityRepository extends JpaRepository<CommentEntity,Integer> {

    //select count(*) from "comment" where post_id = 49
    @Query(value = "SELECT COUNT(*) FROM CommentEntity entity WHERE entity.post =:post")
    Integer countByPost(@Param("post") PostEntity post);

    public Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE CommentEntity entity SET deleted_at = NOW() WHERE entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity postEntity);

}
