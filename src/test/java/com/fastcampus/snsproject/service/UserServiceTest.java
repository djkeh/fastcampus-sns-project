package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.exception.SnsApplicationException;
import com.fastcampus.snsproject.fixture.UserEntityFixture;
import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        String userName = "userName";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, password)));

        Assertions.assertDoesNotThrow(() ->userService.join(userName, password));
    }

    @Test
    void 회원가입시_userName으로_회원가입한_유저가_이미_있는_경우() {
        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName, password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, () ->userService.join(userName, password));
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우() {
        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        //mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn((Optional.of(fixture)));

        Assertions.assertDoesNotThrow(() ->userService.login(userName, password));
    }
    @Test
    void 로그인시_userName으로_회원가입한_유저가_없는_경우() {
        String userName = "userName";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

        Assertions.assertThrows(SnsApplicationException.class, () ->userService.login(userName, password));
    }

    @Test
    void 로그인시_패스워드가_틀린_경우() {
        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrongPassword";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        //mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn((Optional.of(fixture)));

        Assertions.assertThrows(SnsApplicationException.class, () ->userService.login(userName, wrongPassword));
    }

}

