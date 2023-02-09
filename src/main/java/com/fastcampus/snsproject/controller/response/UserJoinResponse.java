package com.fastcampus.snsproject.controller.response;

import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {
    private Integer id;
    private String useName;
    private UserRole role;

    public static UserJoinResponse fromUser(User user){
        return new UserJoinResponse(
                user.getId(),
                user.getUsername(),
                user.getUserRole()
        );
    }

}
