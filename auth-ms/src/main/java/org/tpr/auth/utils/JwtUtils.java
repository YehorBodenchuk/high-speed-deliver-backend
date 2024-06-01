package org.tpr.auth.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tpr.auth.models.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public final class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenExpire}")
    private long tokenExpire;

    @Value("${jwt.refreshTokenExpire}")
    private long refreshTokenExpire;

    public String generateToken(User user) {
        return createToken(formClaims(user), user.getEmail(), 1000 * 60 * tokenExpire);
    }

    public String generateRefreshToken(User user) {
        return createToken(formClaims(user), user.getEmail(), 1000 * 60 * 60 * 24 * refreshTokenExpire);
    }

    private Map<String, Object> formClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        return claims;
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }
}
