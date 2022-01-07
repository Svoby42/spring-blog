package com.example.springblog.controllers;

import com.example.springblog.services.IArticleService;
import com.example.springblog.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final
    IArticleService articleService;

    final
    IUserService userService;

    public UserController(IArticleService articleService, IUserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllArticlesOfUser(@PathVariable String username){
        return new ResponseEntity<>(articleService.findAllArticlesOfUser(username), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        articleService.deleteAllArticlesOfUser(username);
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
