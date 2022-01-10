package com.example.springblog.controllers;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.User;
import com.example.springblog.services.IArticleService;
import com.example.springblog.services.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{slug}")
    public ResponseEntity<?> getArticle(@PathVariable String slug){
        Optional<Article> article = articleService.findArticleBySlug(slug);
        if(article.isPresent()){
            return new ResponseEntity<>(article.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> saveArticle(@RequestBody Article article){
        Optional<Article> foundArticle = articleService.findArticleByTitle(article.getTitle());
        if(foundArticle.isPresent()){
            return new ResponseEntity<>("Article with the title already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(articleService.saveArticle(article), HttpStatus.CREATED);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<?> updateArticle(@PathVariable String slug, @RequestBody Article article){
        return new ResponseEntity<>(articleService.updateArticle(article), HttpStatus.OK);
    }

    @DeleteMapping("/{articleUUID}")
    public ResponseEntity<?> deleteArticle(@PathVariable String articleUUID){
        User signedInUser = authenticationService.getSignedInUser();
        User articleAuthor = articleService.findAuthorOfArticle(articleUUID);

        if(signedInUser.getId() == articleAuthor.getId()){
            articleService.deleteArticle(articleUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Article is not yours!", HttpStatus.UNAUTHORIZED);
    }


}
