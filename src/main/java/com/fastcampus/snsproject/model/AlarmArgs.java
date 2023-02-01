package com.fastcampus.snsproject.model;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {

    //알람 발생시킨 사람
    private Integer fromUserId;
    //알람을 받을 사람
    private Integer targetId;


}
