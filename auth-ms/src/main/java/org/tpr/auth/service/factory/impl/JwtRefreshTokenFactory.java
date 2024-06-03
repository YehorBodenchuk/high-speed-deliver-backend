package org.tpr.auth.service.factory.impl;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tpr.auth.model.User;
import org.tpr.auth.model.enums.TokenType;
import org.tpr.auth.service.factory.TokenFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtRefreshTokenFactory implements TokenFactory {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.refreshTokenExpire}")
    private long refreshTokenExpire;

    @Override
    public String generateToken(User user) {
        return createToken(
                formClaims(user),
                user.getEmail(),
                1000 * 60 * 60 * 24 * refreshTokenExpire,
                getSecretKey()
        );
    }

    @Override
    public Map<String, Object> formClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("roles", user.getRoles());
        claims.put("type", TokenType.REFRESH);
        return claims;
    }

    @Override
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
