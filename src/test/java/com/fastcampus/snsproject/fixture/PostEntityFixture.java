package com.fastcampus.snsproject.fixture;

import com.fastcampus.snsproject.model.UserRole;
import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;

public class PostEntityFixture {

    public static PostEntity get(String userName, Integer postId, Integer userId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUserName(userName);


        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);
        //title 과 body는 mock에서 큰 의미 없으므로 제외했음
        return result;
    }


}
