package com.fastcampus.snsproject.service;


import com.fastcampus.snsproject.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User join() {
        return new User();
    }

}
