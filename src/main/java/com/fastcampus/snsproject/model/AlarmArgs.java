package com.fastcampus.snsproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmArgs {

    // 알람을 발생식킨 사람
    private Integer fromUserId;
    private Integer targetId;

}
