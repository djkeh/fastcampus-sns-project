package com.fastcampus.snsproject.util;

/**
 * 강의 샘플로 만든 유틸리티 클래스
 *
 * @author uno
 */
public class StringUtils {

    /**
     * 입력 문자열이 {@code uno}일 경우, 대문자로 강조해준다.
     *
     * @param input 입력문자열
     * @return uno일 경우 UNO, 아니면 그대로 리턴.
     */
    public static String uppercase(String input) {
        if (input.equals("uno")) {
            return input.toUpperCase();
        }

        return input;
    }

}
