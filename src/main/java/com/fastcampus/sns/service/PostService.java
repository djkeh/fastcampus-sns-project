package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.Post;
import com.fastcampus.sns.model.entity.PostEntity;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.PostEntityRepository;
import com.fastcampus.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;

    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName){

        //user find
        UserEntity userEntity =
        userEntityRepository.findByUserName(userName).orElseThrow(()-> new SnsApplicationException(ErrorCode.USER_NOT_FOUND,
                String.format("%s not founded", userName)));
        //post save
        postEntityRepository.save(PostEntity.of(title,body,userEntity));


    }

    @Transactional
    public Post modify(String title, String body, String userName, Integer postId){
        UserEntity userEntity =userEntityRepository.findByUserName(userName).orElseThrow(()->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded", userName)));
        //post exist
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(()->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND,String.format("%s not founded", postId)));

        //post permission
        if(postEntity.getUser() != userEntity){
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s",
                    userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

       ;
        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));

    }


}
