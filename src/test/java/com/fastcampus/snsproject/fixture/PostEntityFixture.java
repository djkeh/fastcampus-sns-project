package com.fastcampus.snsproject.fixture;

import lombok.Data;

public class PostEntityFixture {

    public static PostInfo get() {
        PostInfo info = new PostInfo();
        info.setPostId(1);
        info.setUserId(5);
        info.setUserName("name");
        info.setPassword("password");
        info.setTitle("title");
        info.setBody("body");
        return info;
    }

    @Data
    public static class PostInfo {
        private Integer postId;
        private Integer userId;
        private String userName;
        private String password;
        private String title;
        private String body;
    }
}
