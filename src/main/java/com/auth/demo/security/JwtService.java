package com.auth.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.security.KeyRep.Type.SECRET;

@Service
public class JwtService {
     private static final String SECRET = "THIS_IS_A_VERY_LONG_AND_SECURE_SECRET_KEY_123456";
     private static final int EXPIRATION_TIME = 100*60*15;

     public String generateToken(UserDetails userDetails) {
         return Jwts.builder()
                 .setSubject(userDetails.getUsername())
                 .setIssuedAt(new Date())
                 .setExpiration(
                         new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                 )
                 .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                 .compact();
     }
     //extract username / email from token
     public String extractUsername(String token) {
         return extractClaims(token).getSubject();
     }

     //validate token
    public boolean isTokenValid(String token,UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername())
        && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token) {
         return extractClaims(token)
                 .getExpiration()
                 .before(new Date());
    }
    private Claims extractClaims(String token) {
         return Jwts.parserBuilder()
                 .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
    }

}
