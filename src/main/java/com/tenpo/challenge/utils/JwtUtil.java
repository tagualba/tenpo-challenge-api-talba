package com.tenpo.challenge.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@AllArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.ttl}")
    private Long ttl;
    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createApiToken(String email) {
        Map<String, Object> claims = Jwts.claims().setSubject(email);
        Date dateNow = new Date();
        Date dateExpired = new Date(dateNow.getTime() + ttl);

        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(dateNow)
                   .setExpiration(dateExpired)
                   .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(secret))
                   .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
