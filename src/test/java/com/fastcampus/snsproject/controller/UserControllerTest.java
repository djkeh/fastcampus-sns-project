package com.fastcampus.snsproject.controller;


import com.fastcampus.snsproject.controller.request.UserJoinRequest;
import com.fastcampus.snsproject.exception.SnsApplicationException;
import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @MockBean UserService userService;

    @Test
    public void 회원가입() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.join()).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입시_이미_회원가입된_userName으로_회원가입을_하는경우_에러반환() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.join().thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
        ).andDo(print())
                .andExpect(status().isConflict());
    }
}
