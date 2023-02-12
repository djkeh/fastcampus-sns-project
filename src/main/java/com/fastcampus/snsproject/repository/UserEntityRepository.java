package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByUserName(String userName);
}