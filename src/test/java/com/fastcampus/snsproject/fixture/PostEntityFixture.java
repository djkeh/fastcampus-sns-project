package com.fastcampus.snsproject.fixture;

import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;

public class PostEntityFixture {

    public static PostEntity get(String usename, Integer postId, Integer userId){
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUserName(usename);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);

        return result;
    }
}
