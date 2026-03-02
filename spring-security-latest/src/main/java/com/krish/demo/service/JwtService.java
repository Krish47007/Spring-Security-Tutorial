package com.krish.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.claims;

@Service
public class JwtService {

    //256bit secret key
    private static final String BASE64_SECRET = "3dw27P5968TF65UhSlpGusyd3FntEfUeG2b2qHqx72c=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String userName)
    {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {

        Date currentTime = new Date(System.currentTimeMillis());
        Instant instant = currentTime.toInstant().plus(Duration.ofMinutes(30));
        Date expirationTime = Date.from(instant);

       return  Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(currentTime)
                .setExpiration(expirationTime)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    //This will return signed key based on our own secret
    private Key getSignKey() {

        byte[] keyBytes = Decoders.BASE64.decode(BASE64_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
