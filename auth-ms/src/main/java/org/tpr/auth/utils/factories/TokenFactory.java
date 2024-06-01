package org.tpr.auth.utils.factories;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.tpr.auth.models.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface TokenFactory {

    String generateToken(User user);

    Map<String, Object> formClaims(User user);

    Boolean validateToken(String token);

    SecretKey getSecretKey();

    default Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    };

    default String createToken(Map<String, Object> claims, String subject, long expirationTime, SecretKey secret) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secret)
                .compact();
    }

    default String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    default <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    default Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    default Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
