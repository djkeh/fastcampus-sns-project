package com.fastcampus.snsproject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateRequest {
    private String title;
    private String body;
}
