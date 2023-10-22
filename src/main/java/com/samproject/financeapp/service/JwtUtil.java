package com.samproject.financeapp.service;

import com.samproject.financeapp.security.JwtKeyManager;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {

    private final JwtKeyManager jwtKeyManager;


    @Value("${jwt.expiration}")
    private Long expirationTime;

    public JwtUtil(JwtKeyManager jwtKeyManager) {
        this.jwtKeyManager = jwtKeyManager;
    }

    public String generateToken(String googleId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);
        Key key = jwtKeyManager.loadOrGenerateKey();
        return Jwts.builder()
                .subject(googleId)
                .signWith(key)
                .compact();
    }

    public String parseToken(String token) {
       try{
           String googleId = Jwts.parser().verifyWith(jwtKeyManager.loadOrGenerateKey()).build().parseSignedClaims(token).getPayload().getSubject();
           return googleId;
       }
       catch (JwtException e){
           throw new RuntimeException("Invalid token");
        }
    }

}
