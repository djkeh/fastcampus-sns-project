package com.fastcampus.snsproject.repository;

import com.fastcampus.snsproject.model.entity.AlarmEntity;
import com.fastcampus.snsproject.model.entity.LikeEntiity;
import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Integer> {
    //Page<AlarmEntity> findAllByUser(UserEntity user, Pageable pageable);
    Page<AlarmEntity> findAllByUserId(Integer userId, Pageable pageable);

}
