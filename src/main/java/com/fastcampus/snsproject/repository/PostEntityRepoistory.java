package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityRepoistory extends JpaRepository<PostEntity, Integer> {

    Page<PostEntity> findAllByUser(UserEntity entity, Pageable pageable);

}
