package com.fastcampus.snsproject.configuration.filter;

import com.fastcampus.snsproject.model.User;
import com.fastcampus.snsproject.service.UserService;
import com.fastcampus.snsproject.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String key;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if( header == null || !header.startsWith("Bearer ")) {
            log.error("Error occurs whiles getting header. header is null or invalid");
            filterChain.doFilter(request,response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();



            // TODO : get userName from token
            String userName = JwtTokenUtils.getUsername(token,key);
            // TODO : check the User is valid
            User user = userService.loadUserByUserName(userName);


            // TODO : check token is vaild
            if(JwtTokenUtils.isTokenExpired(token, key)){
                log.error("Key is expired");
                filterChain.doFilter(request,response);
                return;
            };

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    // TODO : principal, credentials, authorities 세팅
                    user, null, user.getAuthorities());


            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (RuntimeException e) {
            log.error("Error occurs while validating. {}", e.toString());
        }
        //

        // 뒤의 필터들로 넘긴다
        filterChain.doFilter(request, response);
    }
}
