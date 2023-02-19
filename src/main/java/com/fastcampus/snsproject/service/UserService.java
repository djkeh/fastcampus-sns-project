package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.exception.ErrorCode;
import com.fastcampus.snsproject.model.Alarm;
import com.fastcampus.snsproject.model.AlarmArgs;
import com.fastcampus.snsproject.model.entity.AlarmEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.exception.SnsApplicationException;
import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.repository.AlarmEntityRepository;
import com.fastcampus.snsproject.repository.UserEntityRepository;
import com.fastcampus.snsproject.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public User loadUserByUserName(String userName) {
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not fined", userName)));
    }


    @Transactional
    public User join(String userName, String password) {
        // 회원가입하려는 userName 으로 회원가입된 user 가 있는지
        //Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userName);
        userEntityRepository.findByUserName(userName)
                .ifPresent(it -> {
                    throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME
                            , String.format("%s is duplicated",userName));
                });

        // 회원가입 진행 = user 를 등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));

//        throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("임시오류"));
        //return new User();
        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName)));

        // 비밀번호 체크
        // if(!userEntity.getPassword().equals(password)) {
        if (!encoder.matches(password, userEntity.getPassword())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // 로그인 성공시 토큰 생성 및 반환
        String token = JwtTokenUtils.generateAccessToken(userName, secretKey, expiredTimeMs);
        return token;
    }

    public Page<Alarm> alarmList(Integer userId, Pageable pageable) {
        // check user
//        UserEntity userEntity = userEntityRepository.findByUserName(userName)
//                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName)));
        return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);

    }


}
