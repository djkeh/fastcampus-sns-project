package com.fastcampus.snsproject.controller.reqeust;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentRequest {

    private String comment;
}
