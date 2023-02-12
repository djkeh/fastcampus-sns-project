package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.exception.ErrorCode;
import com.fastcampus.snsproject.exception.SnsApplicationException;
import com.fastcampus.snsproject.model.Alarm;
import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.repository.AlarmEntityRepository;
import com.fastcampus.snsproject.repository.UserCacheRepository;
import com.fastcampus.snsproject.repository.UserEntityRepository;
import com.fastcampus.snsproject.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;
  private final BCryptPasswordEncoder encoder;
  private final UserCacheRepository userCacheRepository;

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.token.expired-time-ms}")
  private Long expiredTimeMs;


  public User loadUserByUserName(String userName) {
    return userCacheRepository.getUser(userName).orElseGet(() ->
        userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
            new SnsApplicationException(ErrorCode.USER_NOT_FOUND,
                String.format("%s not founded", userName)))
    );
  }

  @Transactional
  public User join(String userName, String password) {
    // 1. userName 존재?
    userEntityRepository.findByUserName(userName).ifPresent(it -> {
      throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,
          String.format("%s이 중복 되었습니다.", userName));
    });

    // 2. 회원가입
    UserEntity userEntity = userEntityRepository.save(
        UserEntity.of(userName, encoder.encode(password)));

    return User.fromEntity(userEntity);
  }

  public String login(String userName, String password) {

    UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
        new SnsApplicationException(ErrorCode.USER_NOT_FOUND,
            String.format("%s is not founded", userName)));

    // 회원가입 여부 체크
//    User user = loadUserByUserName(userName);
//    userCacheRepository.setUser(user);

    // 2.비밀번호 체크
    if (!encoder.matches(password, userEntity.getPassword())) {
      throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
    }

    // 3.토큰 생성
    return JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs);
  }
  public Page<Alarm> alarmList(Integer userId, Pageable pageable) {
    return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);

  }
}
