package com.fastcampus.snsproject.fixture;

import com.fastcampus.snsproject.model.UserRole;
import com.fastcampus.snsproject.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;

public class UserEntityFixture {

    public static UserEntity get(String userName, String password, Integer userId) {
        UserEntity result = new UserEntity();
        result.setId(userId);
        result.setUserName(userName);
        result.setPassword(password);
        result.setRole(UserRole.USER);
        result.setRegisteredAt(Timestamp.from(Instant.now()));
        return result;
    }


}
