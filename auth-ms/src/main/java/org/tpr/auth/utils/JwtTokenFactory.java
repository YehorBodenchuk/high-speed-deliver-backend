package org.tpr.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tpr.auth.models.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public final class JwtTokenFactory implements TokenFactory {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenExpire}")
    private long tokenExpire;

    @Override
    public String generateToken(User user) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return createToken(formClaims(user), user.getEmail(), 1000 * 60 * tokenExpire, secretKey);
    }

    @Override
    public Map<String, Object> formClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("roles", user.getRoles());
        return claims;
    }

    @Override
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    @Override
    public Claims extractAllClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
