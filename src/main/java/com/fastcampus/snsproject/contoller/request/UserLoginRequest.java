package com.fastcampus.snsproject.contoller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {
    private String name;
    private String password;
}
