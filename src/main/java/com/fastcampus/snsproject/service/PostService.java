package com.fastcampus.snsproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import com.fastcampus.snsproject.exception.ErrorCode;
import com.fastcampus.snsproject.exception.SnsApplicationException;
import com.fastcampus.snsproject.model.Post;
import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.repository.PostEntityRepository;
import com.fastcampus.snsproject.repository.UserEntityRepository;

import lombok.*;

@Service
@RequiredArgsConstructor
public class PostService {
	
	private final PostEntityRepository postEntityRepository;
	private final UserEntityRepository userEntityRepository;
	
	@Transactional
	public void create(String title, String body, String userName) {
		UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> 
			new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));
		
		//postEntityRepository.save(new PostEntity());
		postEntity saved = postEntityRepository.save(PostEntity.of(title, body, userEntity));
	}
	
	@Transactional
	public Post modify(String title, String body, String userName, Integer postId) {
		UserEntity userEntity = userENtityRepository.findByUserName(userName).orElseThrow(() -> 
				new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
		PostEntity postEntity = postEntityRePository.findById(postId).orElseThrow(() ->
			new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
		
		if(postEntity.getUser() != userEntity) {
			throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
		}
		
		postEntity.setTitle(title);
		postEntity.setBody(body);
		
		return Post.fromEntity(postEntityRepository.save(postEntity));

	}
	
	@Transactional
	public Post delete(String userName, Integer postId) {
		UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> 
		new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));
		
		PostEntity postEntity = postEntityRePository.findById(postId).orElseThrow(() ->
		new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
		
		if(postEntity.getUser() != userEntity) {
			throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
		}
		
		postEntityRepository.delete(postEntity);

	}
}
