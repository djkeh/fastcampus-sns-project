package com.fastcampus.snsproject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostModifyRequest {

    private String title;
    private String body;
}
