package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.Alarm;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.AlarmEntityRepository;
import com.fastcampus.sns.repository.UserEntityRepository;
import com.fastcampus.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;

import net.bytebuddy.asm.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
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

    //TODO : implement
    @Transactional
    public User join(String userName, String passWord){
        //회원가입하려는 userName으로 회원가입된 user가 있는지
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPICATED_USER_NAME, String.format("%s. is duplicated ", userName));
        });

        //회원가입 진행 = user 등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(passWord)));

        return User.fromEntity(userEntity);
    }

    //TODO : implement
    public String login(String userName, String passWord){
        //회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%S not found", userName)));


        //비밀번호 체크
        //if(!userEntity.getPassWord().equals(passWord)){
        if(!encoder.matches(passWord, userEntity.getPassWord())){
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD );
        }


        //토큰 생성
        String token = JwtTokenUtils.generageToken(userName, secretKey, expiredTimeMs);

        return token;
    }

    public User loadUserByUserName(String userName){
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not found", userName)));
    }

    public Page<Alarm> alarmList(Integer userId, Pageable pageable) {
    //public Page<Alarm> alarmList(UserEntity userEntity, Pageable pageable) {
        //UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()
        //        -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%S not found", userName)));

        //return alarmEntityRepository.findAllByUser(userEntity, pageable).map(Alarm::fromEntity);
        return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);

    }


}
