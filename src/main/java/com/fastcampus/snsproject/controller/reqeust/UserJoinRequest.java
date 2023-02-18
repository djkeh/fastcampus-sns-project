package com.fastcampus.snsproject.controller.reqeust;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequest {

    private String name;
    private String password;
}
