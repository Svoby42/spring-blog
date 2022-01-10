package com.example.springblog.controllers;

import com.example.springblog.entities.Role;
import com.example.springblog.entities.User;
import com.example.springblog.services.IArticleService;
import com.example.springblog.services.IAuthenticationService;
import com.example.springblog.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final
    IArticleService articleService;

    final
    IUserService userService;

    final IAuthenticationService authenticationService;

    public UserController(IArticleService articleService, IUserService userService, IAuthenticationService authenticationService) {
        this.articleService = articleService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllArticlesOfUser(@PathVariable String username){
        return new ResponseEntity<>(articleService.findAllArticlesOfUser(username), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User user){
        User signedInUser = authenticationService.getSignedInUser();
        User updatingUser = user;

        if(signedInUser.getUsername().equals(updatingUser.getUsername()) ||  signedInUser.getRole().name().equals(Role.ADMIN.name())){
            Optional<User> userOptional = userService.findByUsername(username);
            if(userOptional.isPresent()){
                return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        //articleService.deleteAllArticlesOfUser(username);
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
