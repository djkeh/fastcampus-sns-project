package com.fastcampus.snsproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmArgs {
    private Integer fromUserId;
    private Integer targetId;
}
