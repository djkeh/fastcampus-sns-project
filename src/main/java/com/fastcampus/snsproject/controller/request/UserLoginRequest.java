package com.fastcampus.snsproject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    private String name;
    private String password;
}
