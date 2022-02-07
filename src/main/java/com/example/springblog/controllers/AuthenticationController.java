package com.example.springblog.controllers;

import com.example.springblog.entities.User;
import com.example.springblog.services.IAuthenticationService;
import com.example.springblog.services.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    private final IUserService userService;

    private final PasswordEncoder passwordEncoder;

    @Value("${app.cookie.secure}")
    private boolean secure;

    public AuthenticationController(IAuthenticationService authenticationService, IUserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody User user){
        if(userService.findByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@RequestBody User user, HttpServletResponse response){
        Optional<User> signedInUser = userService.findByUsername(user.getUsername());

        if(signedInUser.isPresent()){
            User fullUser = signedInUser.get();
            if(BCrypt.checkpw(user.getPassword(), fullUser.getPassword())){
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(200));
                userService.updateLastLogin(fullUser.getUsername());
                Cookie cookie = new Cookie("currentUser", authenticationService.signInAndReturnJWT(user).getToken());
                cookie.setHttpOnly(true);
                cookie.setSecure(secure);
                response.addCookie(cookie);
                return new ResponseEntity<>(Collections.singletonMap("token", authenticationService.signInAndReturnJWT(user).getToken()), HttpStatus.OK);
            }
            return new ResponseEntity<>(Collections.singletonMap("message", "bad credentials"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(Collections.singletonMap("message", "bad credentials"), HttpStatus.UNAUTHORIZED);
    }


}
