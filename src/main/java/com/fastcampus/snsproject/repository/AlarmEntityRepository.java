package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AlarmEntityRepository extends JpaRepository<AlarmEntity,Integer> {
    public Page<AlarmEntity> findAllByUser(UserEntity user, Pageable pageable);

    public Page<AlarmEntity> findAllByUserId(Integer userId, Pageable pageable);
}
