package com.fastcampus.snsproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

//획일화된 응답 형태를 만들기 위해 생성
@Getter
@AllArgsConstructor
public class Response<T>{

    private String resultCode;
    private T result;

    public static Response<Void> error(String errorCode) {
        return new Response<>(errorCode, null);
    }

    public static Response<Void> success() {
        return new Response<Void>("SUCESS", null);
    }
    public static <T> Response<T> success(T result) {
        return new Response<T>("SUCESS", result);
    }

    public String toStream() {
        if (result == null) {
            return "{" +
                    "\"resultCode\":" + "\"" + resultCode + "\"," +
                    "\"result\":" + null +
                    "}";
        }
        return "{" +
                "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + result + "\"," +
                "}";
    }

}
