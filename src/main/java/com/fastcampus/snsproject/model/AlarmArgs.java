package com.fastcampus.snsproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmArgs {

    private Integer fromUserId; // 알람을 발생시킨 사람
    private Integer targetId;   // 알람이 발생한 post

}
