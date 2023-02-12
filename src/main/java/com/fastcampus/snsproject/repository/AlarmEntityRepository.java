package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.entity.AlarmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Long> {

    Page<AlarmEntity> findAllByUserId(Integer userId, Pageable pageable);
}
