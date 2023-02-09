package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.entity.CommentEntity;
import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fastcampus.snsproject.model.entity.LikeEntiity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntiity, Integer> {
    Optional<LikeEntiity> findByUserAndPost(UserEntity user, PostEntity post);

    //@Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post =:post")
    //Integer countByPost(@Param("post") PostEntiity post);
    long countByPost(PostEntity post);

    Page<LikeEntiity> findAllByPost(PostEntity post, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE LikeEntiity entity Set deleted_at = NOW() where entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity post);
}
