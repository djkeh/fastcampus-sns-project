package com.fastcampus.snsproject.service;

import org.springframework.stereotype.Service;

@Service
public class LindaStringService implements StringService {
    @Override
    public String uppercase(String input) {
        return "Linda";
    }
}
