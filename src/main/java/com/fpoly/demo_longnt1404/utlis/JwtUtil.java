package com.fpoly.demo_longnt1404.utlis;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Create a secret key for JWT
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // Encode the key to Base64
    private final String secret =  Base64.getEncoder().encodeToString(key.getEncoded());

    @Value("${jwt.expiration}")  // Get expiration time from application.properties
    private long expirationTime;


    // Get username from token payload
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Claims is a class that represents the payload of a JWT
    }

    // Get expiration time from token payload
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Return the expiration time of the token
    }

    // Get claim from token payload
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from token payload
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    // Verify if token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Create token with username and expiration time
    public String generateToken(String username) {
        return Jwts.builder()
                // Set subject of token
                .setSubject(username)
                // Set issued time
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Set expiration time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                // Sign token with secret key
                .signWith(SignatureAlgorithm.HS256, secret)
                // Compact token
                .compact();
    }

    // Validate token with username
    public Boolean validateToken(String token, String username) {
        // Extract username from token
        final String extractedUsername = extractUsername(token);
        // Check if extracted username is equal to username and token is not expired
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}

