package org.tpr.auth.utils.factories.impls;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tpr.auth.models.User;
import org.tpr.auth.models.enums.TokenType;
import org.tpr.auth.utils.factories.TokenFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenFactory implements TokenFactory {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenExpire}")
    private long tokenExpire;

    @Override
    public String generateToken(User user) {
        return createToken(formClaims(user), user.getEmail(), 1000 * 60 * tokenExpire, getSecretKey());
    }

    @Override
    public Map<String, Object> formClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("roles", user.getRoles());
        claims.put("type", TokenType.ACCESS);
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
