package com.fastcampus.sns.exception;

import com.fastcampus.sns.controller.response.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fastcampus.sns.exception.ErrorCode.INVALID_TOKEN;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(INVALID_TOKEN.getStatus().value());
        response.getWriter().write(Response.error(INVALID_TOKEN.name()).toStream());
    }
}
