package com.fastcampus.snsproject.configuration.filter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fastcampus.snsproject.service.UserService;
import com.fastcampus.snsproject.util.JwtTokenUtils;

import lombok.extern.slf4j.Slf4j

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter{
	
	private final String key;
	private final UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(header == null || !header.startsWith("Bearer ")) {
			log.error("Error header is null or invalid");
			filterChain.doFilter(request, response);
			return;
		}
		
		try{
			final String token = header.split(" ")[1].trim();
			
			if(JwtTokenUtils.isExpired(token, key){
				log.error("error");
				filterChain.doFilter(reqeust, response);
				return;
			}
			
			String username = JwtTokenUtils.getUserName(token, key);
			User user = userService.loadUserByUserName(userName);
			
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					user, null, List.of(new SimpleGrantedAuthority(user.getUserRole().toString())));
			
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch(RuntimeException e) {
			log.error("error. {}", e.toString());
			filterChain.doFilter(request, response);
			return;
		}
		
		filterChain.doFilter(request, response);
	}

}
