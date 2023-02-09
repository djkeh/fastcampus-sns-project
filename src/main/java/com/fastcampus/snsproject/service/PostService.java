package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.exception.ErrorCode;
import com.fastcampus.snsproject.exception.SnsApplicationException;
import com.fastcampus.snsproject.model.AlarmArgs;
import com.fastcampus.snsproject.model.AlarmType;
import com.fastcampus.snsproject.model.Comment;
import com.fastcampus.snsproject.model.Post;
import com.fastcampus.snsproject.model.entity.*;
import com.fastcampus.snsproject.model.event.AlarmEvent;
import com.fastcampus.snsproject.producer.AlarmProducer;
import com.fastcampus.snsproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final LikeEntityRepository likeEntityRepository;
    private final CommentEntityRepository commentEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final AlarmProducer alarmProducer;

    @Transactional
    public void create(String title, String body, String userName){
        //User Find
        UserEntity userEntity =  getUserEntityOrException(userName);
        //post Save
        postEntityRepository.save(PostEntity.of(title, body, userEntity));
    }
    @Transactional
    public Post modify(String title, String body, String userName, Integer postId){

        UserEntity userEntity =  getUserEntityOrException(userName);
        // post exists
        PostEntity postEntity =getPostEntityOrException(postId);

        //post permission
        if(postEntity.getUser() != userEntity){
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION,String.format("%s has no permission with %s",userName, postId));
        }
        //post Save
        postEntity.setTitle(title);
        postEntity.setBody(body);
        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));

    }

    @Transactional
    public void delete(String userName, Integer postId){

        UserEntity userEntity =  getUserEntityOrException(userName);
        // post exists
        PostEntity postEntity =getPostEntityOrException(postId);

        //post permission
        if(postEntity.getUser() != userEntity){
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION,String.format("%s has no permission with %s",userName, postId));
        }
        //post Delete
        postEntityRepository.delete(postEntity);
        likeEntityRepository.deleteAllByPost(postEntity);
        commentEntityRepository.deleteAllByPost(postEntity);

    }

    public Page<Post> list(Pageable pageable){
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String userName, Pageable pageable){
        UserEntity userEntity =  userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUNED, String.format("%s not founded", userName)));

        return postEntityRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }
    @Transactional
    public void like(Integer postId, String userName){
        // post exists
        PostEntity postEntity =getPostEntityOrException(postId);

        UserEntity userEntity =  getUserEntityOrException(userName);

        likeEntityRepository.findByUserAndPost(userEntity, postEntity).ifPresent(it -> {
                throw new SnsApplicationException((ErrorCode.ALREADY_LIKED),String.format("username %s already like post %d", userName, postId));
        });

        likeEntityRepository.save(LikeEntiity.of(userEntity, postEntity));
        //alarmEntityRepository.save(AlarmEntity.of(postEntity.getUser(), AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(userEntity.getId(), postEntity.getId())));
        alarmProducer.send(new AlarmEvent(postEntity.getUser().getId(), AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(userEntity.getId(), postEntity.getId())));
    }

    public long likeCount(Integer postId){
        // post exists
        PostEntity postEntity =getPostEntityOrException(postId);

        // like count
//        List<LikeEntiity> likeEntiityList = likeEntityRepository.findAllByPost(postEntity);
//        return likeEntiityList.size();
        return likeEntityRepository.countByPost(postEntity);
    }

    @Transactional
    public void comment(Integer postId, String userName,String comment){
        // post exists
        PostEntity postEntity = getPostEntityOrException(postId);
        UserEntity userEntity =  getUserEntityOrException(userName);
        commentEntityRepository.save(CommentEntity.of(userEntity, postEntity,comment));
        //alarmEntityRepository.save(AlarmEntity.of(postEntity.getUser(), AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(userEntity.getId(), postEntity.getId())));
        alarmProducer.send(new AlarmEvent(postEntity.getUser().getId(), AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(userEntity.getId(), postEntity.getId())));

    }

    public Page<Comment> getComments(Integer postId, Pageable pageable){
        // post exists
        PostEntity postEntity = getPostEntityOrException(postId);
        return commentEntityRepository.findAllByPost(postEntity,pageable).map(Comment::fromEntity);
    }

    // post exist
    private PostEntity getPostEntityOrException(Integer postId) {
        return postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
    }

    // user exist
    private UserEntity getUserEntityOrException(String userName) {
        return userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUNED, String.format("%s not founded", userName)));
    }

}
