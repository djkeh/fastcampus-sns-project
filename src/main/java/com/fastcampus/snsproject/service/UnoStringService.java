package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.util.UnoStringUtils;
import org.springframework.stereotype.Service;

@Service
public class UnoStringService implements StringService {
    @Override
    public String replaceBlankSpaces(String input, String replaceWith) {
        return UnoStringUtils.replaceBlankSpaces(input, replaceWith);
    }
}
