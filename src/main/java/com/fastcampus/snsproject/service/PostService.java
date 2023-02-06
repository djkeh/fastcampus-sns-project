package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.excpetion.ErrorCode;
import com.fastcampus.snsproject.excpetion.SnsApplicationException;
import com.fastcampus.snsproject.model.Post;
import com.fastcampus.snsproject.model.entity.LikeEntity;
import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.repository.LikeEntityRepository;
import com.fastcampus.snsproject.repository.PostEntityRepoistory;
import com.fastcampus.snsproject.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepoistory postEntityRepoistory;

    private final UserEntityRepository userEntityRepository;

    private final LikeEntityRepository likeEntityRepository;

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


    @Transactional
    public void like(Integer postId, String userName) {

        // user exist
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NO_FOUND, String.format("%s not founded", userName)));

        //post exist
        PostEntity postEntity = postEntityRepoistory.findById(postId)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        //check liked -> throw exception
        likeEntityRepository.findByUserAndPost(userEntity, postEntity).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.ALREADY_LIKED, String.format("userName %s already like post %d", userName, postId));
        });

        // like save
        likeEntityRepository.save(LikeEntity.of(userEntity, postEntity));
        //alarmEntityRepository.save(AlarmEntity.of(postEntity.getUser(), AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(userEntity.getId(), postEntity.getId())));


    }

    public int likeCount(Integer postId) {

        //post exist
        PostEntity postEntity = postEntityRepoistory.findById(postId)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

        //이렇게하면 모든 Entity 정보를 다가져옴
        //        List<LikeEntity> likeEntities = likeEntityRepository.findAllByPost(postEntity);
        //        return likeEntities.size();
        return likeEntityRepository.countByPost(postEntity);

    }
}