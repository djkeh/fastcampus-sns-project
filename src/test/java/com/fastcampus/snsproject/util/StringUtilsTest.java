package com.fastcampus.snsproject.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SNS 프로젝트 - 문자열 유틸리티 테스트")
class StringUtilsTest {

    @DisplayName("입력 문자열이 'uno'면, 대문자로 치환해준다.")
    @Test
    void givenUnoString_whenCallingMethod_thenReturnsUppercaseString() {
        // Given
        String input = "uno";

        // When
        String actual = StringUtils.uppercase(input);

        // Then
        assertEquals("UNO", actual);
    }

    @DisplayName("입력 문자열이 'uno'가 아니면, 그대로 돌려준다.")
    @Test
    void givenAString_whenCallingMethod_thenReturnsItself() {
        // Given

        // When
        String actual = StringUtils.uppercase("asdf");

        // Then
        assertEquals("asdf", actual);
    }
}