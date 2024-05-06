package com.example.cinemaressys.security;

import com.example.cinemaressys.dtos.jwt.JwtClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import com.example.cinemaressys.entities.User;
import java.util.Date;
import java.security.Key;


public class JwtTokenProvider {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 864000000; // 10 dni

    // Generowanie tokena JWT
    public static String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().getName()) // Dodaj claim z rolą użytkownika
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static JwtClaims decodeJwtToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);

        String userId = claims.getBody().getSubject();
        String role = claims.getBody().get("role", String.class);

        return new JwtClaims(userId, role);
    }
}
