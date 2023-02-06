package com.fastcampus.snsproject.fixture;

import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;

public class PostEntityFixture {


    public static PostEntity get(String userName, Integer postId, Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUserName(userName);


        PostEntity postEntity = new PostEntity();
        postEntity.setUser(userEntity);
        postEntity.setId(postId);

        return postEntity;
    }
}
