package com.fastcampus.snsproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AlarmArgs {
    private Integer fromUserId;
    private Integer targetId;

//    private List<Integer> targetIds;
//    private Integer alarmOccurId;
}
