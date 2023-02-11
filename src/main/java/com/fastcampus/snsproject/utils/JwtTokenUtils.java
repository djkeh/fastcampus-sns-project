package com.fastcampus.snsproject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static String getUserName(String token, String key) {
        return extractAllClaims(token, key).get("userName", String.class);
    }

    public static Boolean isExpired(String token, String key) {
        Date expiredDate = extractAllClaims(token, key).getExpiration();
        return expiredDate.before(new Date());
    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public static String generateAccessToken(String username, String key, long expiredTimeMs) {
        return generateToken(username, key, expiredTimeMs);
    }

    private static String generateToken(String username, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("userName", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
