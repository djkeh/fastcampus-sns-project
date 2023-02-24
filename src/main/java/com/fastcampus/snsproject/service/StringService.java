package com.fastcampus.snsproject.service;

public interface StringService {
    /**
     * 입력 문자열이 {@code uno}일 경우, 대문자로 강조해준다.
     *
     * @param input 입력문자열
     * @return uno일 경우 UNO, 아니면 그대로 리턴.
     */
    String uppercase(String input);
}
