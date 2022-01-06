package com.example.springblog.controllers;

import com.example.springblog.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
