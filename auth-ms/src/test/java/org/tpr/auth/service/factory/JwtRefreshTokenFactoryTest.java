package org.tpr.auth.service.factory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.tpr.auth.model.User;
import org.tpr.auth.model.enums.TokenType;
import org.tpr.auth.service.factory.impl.JwtRefreshTokenFactory;
import org.tpr.auth.util.DataUtil;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@ExtendWith({MockitoExtension.class})
class JwtRefreshTokenFactoryTest {

    @InjectMocks
    private JwtRefreshTokenFactory jwtRefreshTokenFactory;

    private final String SECRET = "your_very_long_secret_key_that_is_at_least_32_bytes_long";

    @BeforeEach
    void setUp() {
        final long expire = 7;
        ReflectionTestUtils.setField(jwtRefreshTokenFactory, "secret", SECRET);
        ReflectionTestUtils.setField(jwtRefreshTokenFactory, "refreshTokenExpire", expire);
    }

    @Test
    void givenUserPersisted_whenGenerateToken_thenRefreshTokenReturn() {
        User persistedUser = DataUtil.getUserPersisted();

        String token = jwtRefreshTokenFactory.generateToken(persistedUser);

        Assertions.assertEquals(extractAllClaims(token).get("type", String.class), TokenType.REFRESH.toString());
    }

    private Claims extractAllClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
