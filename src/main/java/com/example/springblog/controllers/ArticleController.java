package com.example.springblog.controllers;

import com.example.springblog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResponseEntity getAllArticles(){
        return new ResponseEntity<>(articleService.findAllArticles(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllArticlesOfUser(@PathVariable Long userId){
        return new ResponseEntity<>(articleService.findAllArticlesOfUser(userId), HttpStatus.CREATED);
    }

}
