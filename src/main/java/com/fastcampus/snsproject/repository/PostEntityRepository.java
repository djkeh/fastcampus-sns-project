package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.entity.PostEntity;
import org.springframework.*;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Integer>{

}
