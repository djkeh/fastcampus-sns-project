package com.fastcampus.snsproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {

    // Alarm Trigger User
    private Integer fromUserId;

    // Alarm Receive Target
    private Integer targetId;
}
