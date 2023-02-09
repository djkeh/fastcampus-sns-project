package com.fastcampus.snsproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs implements Serializable {

    //알람을 발생시킨 사람
    private Integer fromUserId;
    private Integer postUserId;

}
