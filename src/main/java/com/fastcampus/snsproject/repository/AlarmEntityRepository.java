package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.entity.AlarmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Integer> {

  // 조회 자체를 user 가 아닌 userId로 하여 최적화
  Page<AlarmEntity> findAllByUserId(Integer userId, Pageable pageable);

}
