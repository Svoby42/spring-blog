package com.example.springblog.controllers;

import com.example.springblog.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal")
public class InternalApiController {

    private final IUserService userService;

    public InternalApiController(IUserService userService) {
        this.userService = userService;
    }

    @PutMapping("/make-admin/{username}")
    public ResponseEntity<?> makeAdmin(@PathVariable String username){
        userService.makeAdmin(username);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/make-editor/{username}")
    public ResponseEntity<?> makeEditor(@PathVariable String username){
        userService.makeEditor(username);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/make-user/{username}")                            //i.e. revoke posting rights
    public ResponseEntity<?> makeUser(@PathVariable String username){
        userService.makeUser(username);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
