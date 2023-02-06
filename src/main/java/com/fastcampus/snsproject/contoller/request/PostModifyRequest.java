package com.fastcampus.snsproject.contoller.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostModifyRequest {

    private String title;
    private String body;
}
