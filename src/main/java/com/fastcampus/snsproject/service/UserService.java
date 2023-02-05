package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.Exception.ErrorCode;
import com.fastcampus.snsproject.Exception.SnsApplicationException;
import com.fastcampus.snsproject.Util.JwtTokenUtils;
import com.fastcampus.snsproject.model.Alarm;
import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.repository.AlarmEntityRepository;
import com.fastcampus.snsproject.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final BCryptPasswordEncoder encoder;


    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public User join(String userName, String password){
        //회원가입하려는 userName으로 중복체크
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        //회원가입진행
        UserEntity savedUser = userEntityRepository.save(UserEntity.of(userName,encoder.encode(password)));

        return User.fromEntity(savedUser);
    }

    public String login(String userName, String password) {
        //가입여부체크
        User savedUser = loadUserByUsername(userName);
        //redisRepository.setUser(savedUser);

        //비밀번호 등록
        if (!encoder.matches(password, savedUser.getPassword())) {

            //if (!userEntity.getPassword().equals(password)) {
                throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
            //}
        }
        //토큰생성
        return JwtTokenUtils.generateAccessToken(userName, secretKey, expiredTimeMs);
    }

    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(
                        () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName)));

        /*
        return redisRepository.getUser(userName).orElseGet(
                () -> userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(
                        () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
                ));
        */

    }

    @Transactional
    public Page<Alarm> alarmList(Integer userId, Pageable pageable) {
        return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);
    }

}
