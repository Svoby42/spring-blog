package com.example.springblog.security.jwt;

import com.example.springblog.security.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements IJwtProvider{

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("{app.jwt.expiration-in-minutes}")
    private String JWT_EXPIRATION;

    @Override
    public String generateToken(UserPrincipal userPrincipal) {
        String authorities = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, Integer.parseInt(JWT_EXPIRATION));

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", authorities)
                .claim("userId", userPrincipal.getId())
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean validateToken(HttpServletRequest request) {
        return false;
    }
}
