package com.fastcampus.snsproject.fixture;

import com.fastcampus.snsproject.model.entity.UserEntity;

public class UserEntityFixture {
    public static UserEntity get(String userName, String password, Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        //userEntity.setRole(UserRole.USER);
        //userEntity.setRegisteredAt(Timestamp.from(Instant.now()));
        return userEntity;
    }
}
