package com.fastcampus.sns.fixture;

import com.fastcampus.sns.model.entity.UserEntity;

public class UserEntityFixture {
    public static UserEntity get(String userName, String passWord, Integer userId){
        UserEntity result = new UserEntity();
        result.setId(userId);
        result.setUserName(userName);
        result.setPassWord(passWord);

        return result;
    }

}
