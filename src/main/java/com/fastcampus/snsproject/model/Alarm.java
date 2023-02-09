package com.fastcampus.snsproject.model;

import com.fastcampus.snsproject.model.entity.AlarmEntity;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.Timestamp;
@Slf4j
@Data
@Getter
@AllArgsConstructor
public class Alarm {
    private Integer id;
    private AlarmType alarmType;
    private AlarmArgs args;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlarmEntity entiity){
        return new Alarm(
                entiity.getId(),
                entiity.getAlarmType(),
                entiity.getArgs(),
                entiity.getRegisteredAt(),
                entiity.getUpdatedAt(),
                entiity.getDeletedAt()
        );
    }
}