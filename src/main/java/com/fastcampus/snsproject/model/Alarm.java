package com.fastcampus.snsproject.model;

import com.fastcampus.snsproject.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Alarm {
    private Integer id;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlarmEntity entity) {
        return new Alarm(
                entity.getId(),
                entity.getAlarmType(),
                entity.getAlarmArgs(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
