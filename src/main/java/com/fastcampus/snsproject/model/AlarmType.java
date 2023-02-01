package com.fastcampus.snsproject.model;

import com.fastcampus.snsproject.model.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;


@RequiredArgsConstructor
@Getter
public enum AlarmType {

    NEW_COMMENT_ON_POST("new comment!"),
    NEW_LIKE_ON_POST("new like!"),
    ;

    private final String alarmText;
}
