package com.fastcampus.snsproject.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {
    NEW_COMMENT_ON_POST("New Comment!"),
    NEW_LIKE_ON_POST("New Like!"),
    ;

    private final String alarmText;
}
