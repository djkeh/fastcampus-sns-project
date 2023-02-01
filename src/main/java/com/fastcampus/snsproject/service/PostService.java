package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.exception.ErrorCode;
import com.fastcampus.snsproject.exception.SnsApplicationException;
import com.fastcampus.snsproject.model.AlarmArgs;
import com.fastcampus.snsproject.model.AlarmType;
import com.fastcampus.snsproject.model.Comment;
import com.fastcampus.snsproject.model.Post;
import com.fastcampus.snsproject.model.entity.*;
import com.fastcampus.snsproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final LikeEntityRepository likeEntityRepository;
    private final CommentEntityRepository commentEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    @Transactional
    public void create(String title, String body, String userName){
        //user find
        UserEntity userEntity = getUserEntityOrException(userName);
        //post save
        PostEntity saved = postEntityRepository.save(PostEntity.of(title, body, userEntity));
        //return
    }
    @Transactional
    public Post modify(String title, String body, String userName, Integer postId){
        //user find
        UserEntity userEntity = getUserEntityOrException(userName);
        //post exist
        PostEntity postEntity = getPostEntityOrException(postId);
        //post permission
        if(postEntity.getUser() != userEntity)
        {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String userName, Integer postId){
        //user find
        UserEntity userEntity = getUserEntityOrException(userName);
        //post exist
        PostEntity postEntity = getPostEntityOrException(postId);
        //post permission
        if(postEntity.getUser() != userEntity)
        {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        //like도 delete 해둠
        likeEntityRepository.deleteAllByPost(postEntity);
        //comment도 delete 해야함
        commentEntityRepository.deleteAllByPost(postEntity);

        postEntityRepository.delete(postEntity);
    }


    public Page<Post> list(Pageable pageable)
    {
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String userName, Pageable pageable)
    {
        //user find
        UserEntity userEntity = getUserEntityOrException(userName);

        return postEntityRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }

    @Transactional
    public void like(Integer postId, String userName){
        //post exist
        PostEntity postEntity = getPostEntityOrException(postId);
        //user find
        UserEntity userEntity = getUserEntityOrException(userName);

        //check liked -> throw exception

        likeEntityRepository.findByUserAndPost(userEntity, postEntity).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.ALREADY_LIKED, String.format("userName %s already like post %d", userName, postId));
        });

        // like save
        likeEntityRepository.save(LikeEntity.of(userEntity, postEntity));
        alarmEntityRepository.save(AlarmEntity.of(postEntity.getUser(), AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(userEntity.getId(), postEntity.getId())));
        //alarmEntityRepository.send(alarmEntity.getId(), postEntity.getUser().getId());

    }

    @Transactional
    public long likeCount(Integer postId){

        PostEntity postEntity = getPostEntityOrException(postId);
        //count liked


//        List<LikeEntity> likeEntities = likeEntityRepository.findAllByPost(postEntity);
//        return likeEntities.size();

        return likeEntityRepository.countByPost(postEntity);
    }

    @Transactional
    public void comment(Integer postId, String userName, String comment){

        //post exist
        PostEntity postEntity = getPostEntityOrException(postId);
        //user find
        UserEntity userEntity = getUserEntityOrException(userName);

        //comment save
        commentEntityRepository.save(CommentEntity.of(userEntity, postEntity, comment));

        alarmEntityRepository.save(AlarmEntity.of(postEntity.getUser(), AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(userEntity.getId(), postEntity.getId())));
    }

    public Page<Comment> getComments(Integer postId, Pageable pageable){
        PostEntity postEntity = getPostEntityOrException(postId);
        return commentEntityRepository.findAllByPost(postEntity, pageable).map(Comment::fromEntity);
    }

    //post exist
    private PostEntity getPostEntityOrException(Integer postId){
        return postEntityRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));

    }

    //user exist
    private UserEntity getUserEntityOrException(String userName){
        return userEntityRepository.findByUserName(userName).orElseThrow(()->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

    }
}
