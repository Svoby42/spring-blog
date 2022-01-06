package com.example.springblog.controllers;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.User;
import com.example.springblog.services.IArticleService;
import com.example.springblog.services.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IAuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<?> getAllArticles(){
        return new ResponseEntity<>(articleService.findAllArticles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveArticle(@RequestBody Article article){
        if(articleService.findArticleByTitle(article.getTitle()).isPresent()){
            return new ResponseEntity<>("Article with the title already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(articleService.saveArticle(article), HttpStatus.CREATED);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @RequestBody Article article){
        return new ResponseEntity<>(articleService.saveArticle(article), HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId){
        User signedInUser = authenticationService.getSignedInUser();
        User articleAuthor = articleService.findAuthorOfArticle(articleId);

        if(signedInUser.getId() == articleAuthor.getId()){
            articleService.deleteArticle(articleId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Article is not yours!", HttpStatus.UNAUTHORIZED);
    }


}
