package com.fastcampus.snsproject.util;

import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.nio.charset.*;
import java.security.*;

public class JwtTokenUtils {
	
	public static boolean getUserName(String token, String key) {
		return extractClaims(token, key).get("userName", String.class);
	}
	
	public static boolean isExpired(String token, String key) {
		Date expiredDate = extractClaims(token, key).getExpiration();
		return expiredDate.before(new Date());
	}
	
	public static Claims extractClaims(String token, String key) {
		return Jwts.parserBuilder().setSigningKey(getKey(key))
			.build().parseClaimsJws(token).getBody();
		
	}
	
	private static String generateToken(String userName, String key, long expiredTimeMs) {
		Claims claims = Jwts.cloims();
		claims.put("userName", userName);
		
		return Jwts.bilder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
				.signWith(getKey(key), SignatureAlgorithm.HS256)
				.compact();
	}
}
