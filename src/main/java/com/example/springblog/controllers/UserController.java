package com.example.springblog.controllers;

import com.example.springblog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllArticlesOfUser(@PathVariable Long userId){
        return new ResponseEntity<>(articleService.findAllArticlesOfUser(userId), HttpStatus.OK);
    }

}
