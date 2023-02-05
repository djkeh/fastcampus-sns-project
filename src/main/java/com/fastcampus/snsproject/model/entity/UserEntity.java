package com.fastcampus.snsproject.model.entity;

import com.fastcampus.snsproject.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "SNS_USER")
@Getter
@Setter
//Spring Data JPA에서 delete메소드를 호출할 때에 삭제 대신 미리 정의된 쿼리를 호출시켜 준다.
@SQLDelete(sql = "UPDATE SNS_USER SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "userName")
    private String userName;
    @Column(name = "password")
    private String password;

    //user의 권한정보 : admin, 일반user 등
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    //system column
    @Column(name = "registered_at")
    private Timestamp registeredAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    //JPA 엔티티(Entity)가 비영속(new/transient) 상태에서 영속(managed) 상태가 되는 시점 이전에 실행됩니다.
    @PrePersist
    void registeredAt(){
        this.registeredAt = Timestamp.from(Instant.now());
    }

    //영속 상태의 엔티티를 이용하여 데이터 업데이트를 수행하기 이전에 실행됩니다.
    @PreUpdate
    void updatedAt(){
        this.updatedAt = Timestamp.from(Instant.now());
    }

    //Dto에서 사용을 위한 변환 Method
    public static UserEntity of(String userName, String password){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }

}
