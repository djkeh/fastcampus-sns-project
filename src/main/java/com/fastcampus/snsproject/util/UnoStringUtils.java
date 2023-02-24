package com.fastcampus.snsproject.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UnoStringUtils {

    public String replaceBlankSpaces(String input, String replaceWith) {
        return input.replace(" ", replaceWith);
    }
}
