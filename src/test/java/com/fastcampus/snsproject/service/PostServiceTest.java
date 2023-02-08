package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.excpetion.ErrorCode;
import com.fastcampus.snsproject.excpetion.SnsApplicationException;
import com.fastcampus.snsproject.fixture.PostEntityFixture;
import com.fastcampus.snsproject.fixture.UserEntityFixture;
import com.fastcampus.snsproject.model.entity.PostEntity;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.repository.PostEntityRepoistory;
import com.fastcampus.snsproject.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostEntityRepoistory postEntityRepoistory;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 포스트작성_성공() {
        String title = "title";
        String body = "body";
        String userName = "userName";

        //mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepoistory.saveAndFlush(any())).thenReturn(Optional.of(mock(PostEntity.class)));
        //save와 saveAndFlush 차이

        Assertions.assertDoesNotThrow(() -> postService.create(title, body, userName));

    }

    @Test
    void 포스트작성_요청유저가_존재하지_않는경우() {
        String title = "title";
        String body = "body";
        String userName = "userName";

        //mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postEntityRepoistory.save(any())).thenReturn(Optional.of(mock(PostEntity.class)));

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.create(title, body, userName));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정_성공() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        //mocking

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepoistory.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntityRepoistory.saveAndFlush(any())).thenReturn(postEntity);

        Assertions.assertDoesNotThrow(() -> postService.modify(title, body, userName, postId));
    }


    @Test
    void 포스트수정_유저가_존재하지_않는_경우() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        //mocking

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepoistory.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, userName, postId));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정_권한이_없는_경우() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();
        UserEntity writer = UserEntityFixture.get("writer", "1", 2);
        //mocking

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepoistory.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, userName, postId));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }

    @Test
    void 포스트삭제_성공() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        //mocking

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepoistory.findById(postId)).thenReturn(Optional.of(postEntity));

        Assertions.assertDoesNotThrow(() -> postService.delete(userName, postId));
    }


    @Test
    void 포스트삭제_유저가_존재하지_않는_경우() {

        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        //mocking

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepoistory.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.delete(userName, postId));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트삭제_권한이_없는_경우() {

        String userName = "userName";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();
        UserEntity writer = UserEntityFixture.get("writer", "1", 2);
        //mocking

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepoistory.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.delete(userName, postId));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }


    @Test
    void 피드목록요청_성공한_경우() {
        Pageable pageable = mock(Pageable.class);
        when(postEntityRepoistory.findAll(pageable)).thenReturn(Page.empty());
        Assertions.assertDoesNotThrow(() -> postService.list(pageable));
    }

    @Test
    void 내피드목록요청_성공한_경우() {
        Pageable pageable = mock(Pageable.class);
        UserEntity user = mock(UserEntity.class);
        when(userEntityRepository.findByUserName(any())).thenReturn(Optional.of(user));
        when(postEntityRepoistory.findAllByUser(user, pageable)).thenReturn(Page.empty());
        Assertions.assertDoesNotThrow(() -> postService.my("",pageable));
    }


}
