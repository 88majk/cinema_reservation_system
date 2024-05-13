package com.example.cinemaressys.security;

import com.example.cinemaressys.dtos.jwt.JwtClaims;
import com.example.cinemaressys.exception.MyException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import com.example.cinemaressys.entities.User;
import java.util.Date;
import java.security.Key;


public class JwtTokenProvider {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 10 * 24 * 60 * 60; // 10 dni w sekundach

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
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);

            String email = claims.getBody().getSubject();
            String role = claims.getBody().get("role", String.class);

            return new JwtClaims(email, role);
        } catch (ExpiredJwtException ex) {
            // Token wygasł
            throw new MyException("Token expired");
        } catch (MalformedJwtException | SecurityException ex) {
            // Niepoprawny token
            throw new MyException("Token incorrect");
        }
    }

}
