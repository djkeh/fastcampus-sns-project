package com.fastcampus.snsproject.service;

import org.springframework.stereotype.Service;

@Service
public class MarkStringService implements StringService {
    @Override
    public String replaceBlankSpaces(String input, String replaceWith) {
        return "MARK";
    }
}
