package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.excpetion.ErrorCode;
import com.fastcampus.snsproject.excpetion.SnsApplicationException;
import com.fastcampus.snsproject.model.Post;
import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.repository.PostEntityRepoistory;
import com.fastcampus.snsproject.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepoistory postEntityRepoistory;

    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {

        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NO_FOUND, String.format("%s not founded", userName)));

        PostEntity postEntity = postEntityRepoistory.save(PostEntity.of(title, body, userEntity));

    }

    @Transactional
    public Post modify(String title, String body, String userName, Integer postId) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NO_FOUND, String.format("%s not founded", userName)));

        //post exist
        PostEntity postEntity = postEntityRepoistory.findById(postId)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        //post permission

        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s hs no permission with %s", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepoistory.saveAndFlush(postEntity));//saveAndFlush??
    }

    @Transactional
    public void delete(String userName, Integer postId) {

        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NO_FOUND, String.format("%s not founded", userName)));

        //post exist
        PostEntity postEntity = postEntityRepoistory.findById(postId)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s hs no permission with %s", userName, postId));
        }

        postEntityRepoistory.delete(postEntity);
    }

    public Page<Post> list(Pageable pageable) {
        return postEntityRepoistory.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String userName, Pageable pageable) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NO_FOUND, String.format("%s not founded", userName)));


        return postEntityRepoistory.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }
}