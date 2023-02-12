package com.fastcampus.snsproject.fixture;

import com.fastcampus.snsproject.model.UserRole;
import com.fastcampus.snsproject.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;

public class UserEntityFixture {

    public static UserEntity get(String userName, String password) {
        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setUserName(userName);
        entity.setPassword(password);
        entity.setRole(UserRole.USER);
        entity.setRegisteredAt(Timestamp.from(Instant.now()));
        return entity;
    }
}
