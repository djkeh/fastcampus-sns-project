package com.fastcampus.snsproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {

    // 알람을 발생시킨 사람
    private Integer fromUserId;
    private Integer targetId;//post id, comment id 유연하게 하기 위해

}
//comment : 동현씨가 새 코멘트를 작성했습니다.->postid, commentid