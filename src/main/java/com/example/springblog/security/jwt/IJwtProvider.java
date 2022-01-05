package com.example.springblog.security.jwt;

import com.example.springblog.security.UserPrincipal;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface IJwtProvider {

    String generateToken(UserPrincipal userPrincipal);
    Authentication getAuthentication(HttpServletRequest request);
    boolean validateToken(HttpServletRequest request);

}
