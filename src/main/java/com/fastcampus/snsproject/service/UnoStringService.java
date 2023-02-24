package com.fastcampus.snsproject.service;

import com.fastcampus.snsproject.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UnoStringService implements StringService {

    @Override
    public String uppercase(String input) {
        return StringUtils.uppercase(input);
    }

}
