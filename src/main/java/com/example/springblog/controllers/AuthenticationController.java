package com.example.springblog.controllers;

import com.example.springblog.entities.User;
import com.example.springblog.services.IAuthenticationService;
import com.example.springblog.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    private final IUserService userService;

    private final PasswordEncoder passwordEncoder;


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
    public ResponseEntity<?> signIn(@RequestBody User user){
        Optional<User> signedInUser = userService.findByUsername(user.getUsername());

        if(signedInUser.isPresent()){
            User fullUser = signedInUser.get();
            if(BCrypt.checkpw(user.getPassword(), fullUser.getPassword())){
                userService.updateLastLogin(fullUser.getUsername());
                return new ResponseEntity<>(authenticationService.signInAndReturnJWT(user), HttpStatus.OK);
            }
            return new ResponseEntity<>(Collections.singletonMap("message", "bad credentials"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(Collections.singletonMap("message", "bad credentials"), HttpStatus.UNAUTHORIZED);
    }


}
