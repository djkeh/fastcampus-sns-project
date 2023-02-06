package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.fixture.UserEntityFixture;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.text.html.Option;

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

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        String userName = "userName";
        String password = "password";

        // moking
        when(userEntityRepository.findByUsername(userName)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, password, 1)));

        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));

    }

    @Test
    void 회원가입시_UserName으로_회원가입한_유저가_이미_있는경우() {
        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName, password, 1);

        // moking
        when(userEntityRepository.findByUsername(userName)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(userName, password));

    }


    @Test
    void 로그인이_정상적으로_동작하는_경우() {
        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName, password, 1);

        // moking
        when(userEntityRepository.findByUsername(userName)).thenReturn(Optional.of(fixture));
        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));

    }

    @Test
    void 로그인시_UserName으로_회원가입한_유저가_이미_없는경우() {
        String userName = "userName";
        String password = "password";

        // moking
        when(userEntityRepository.findByUsername(userName)).thenReturn(Optional.empty());

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));

    }


    @Test
    void 로그인시_패스워드가_틀린경우() {
        String userName = "userName";
        String password = "password";
        String wrongPassword = "password";

        UserEntity fixture = UserEntityFixture.get(userName, password, 1);


        // moking
        when(userEntityRepository.findByUsername(userName)).thenReturn(Optional.of(fixture));
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, wrongPassword));

    }

}
