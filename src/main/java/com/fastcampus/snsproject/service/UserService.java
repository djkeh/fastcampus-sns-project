package com.fastcampus.snsproject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fastcampus.snsproject.exception.ErrorCode;
import com.fastcampus.snsproject.exception.SnsApplicationException;
import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.repository.UserEntityRepository;

import lombok.*;


@Service
@RequiredArgsConstructor
public class UserService{
	
	private final UserEntityRepository userEntityRepository;
	private final BCryptPasswordEncoder encoder;
	
	@Value("${jwt.secret-key}")
	private String secretKey;
	
	@Value("${jwt.token.expired-time-ms}")
	private Long expiredTimeMs;
	
	public User loadUserByUserName(Stirng userName) {
		return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() -> 
			new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
	}
	
	public User join(String userName, String password) {
		//Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userName);
		
		userEntityRepository.findByUserName(userName).ifPresent(it ->{
			throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
		});
		UserEntity userEntity = userEntityRepository.save(UserEntity.of(username, password));
		return User.fromEntity(userEntity);
	}
	
	public String login(String userName, String password) {
		UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));
		
		if(!userEntity.getPassword().equals(password)) {
			throw new SnsApplicationException();
			
		}
	}
	
	
	
}
