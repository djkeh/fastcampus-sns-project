package com.fastcampus.snsproject.controller.response;

import com.fastcampus.snsproject.model.Alarm;
import com.fastcampus.snsproject.model.AlarmArgs;
import com.fastcampus.snsproject.model.AlarmType;
import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.model.entity.AlarmEntity;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@Data
public class AlarmResponse {
    private Integer id;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private String text;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static AlarmResponse fromAlarm(Alarm alarm){
        return new AlarmResponse(
                alarm.getId(),
                alarm.getAlarmType(),
                alarm.getArgs(),
                alarm.getAlarmType().getAlarmText(),
                alarm.getRegisteredAt(),
                alarm.getUpdatedAt(),
                alarm.getDeletedAt()
        );
    }
}