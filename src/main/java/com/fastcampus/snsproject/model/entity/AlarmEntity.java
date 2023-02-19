package com.fastcampus.snsproject.model.entity;

import com.fastcampus.snsproject.model.AlarmArgs;
import com.fastcampus.snsproject.model.AlarmType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
 @Table(name ="\"alarm\"", indexes = {
         @Index(name = "user_id_idx", columnList = "user_id")
 })
@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = NOW() where id =?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class AlarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 알람을 받는 사람
    @ManyToOne(fetch = FetchType.LAZY) // user정보가 필요할때 조회
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private AlarmArgs args;

    @Column(name = "registered_at")
    private Timestamp registeredAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updateAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


    // AlarmEntity 를 담아가는 메서드
    public static AlarmEntity of(UserEntity userEntity, AlarmType alarmType, AlarmArgs args){
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setUser(userEntity);
        alarmEntity.setAlarmType(alarmType);
        alarmEntity.setArgs(args);
        return alarmEntity;
    }
}
