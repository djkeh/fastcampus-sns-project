package com.fastcampus.snsproject.service;

/**
 * 강의를 위해 만든 연습용 PSA 인터페이스
 *
 * @author uno
 */
@FunctionalInterface
public interface StringService {

    /**
     * 입력 문자열의 공백 문자를 처리해주는 메소드
     *
     * @param input 입력 문자열
     * @param replaceWith 대체하고 싶은 문자열
     * @return 공백 문자가 replaceWith로 대체된 문자열
     */
    String replaceBlankSpaces(String input, String replaceWith);
}
