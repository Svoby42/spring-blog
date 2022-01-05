package com.example.springblog.controllers;

import com.example.springblog.entities.Article;
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
    public ResponseEntity<?> getAllArticles(){
        return new ResponseEntity<>(articleService.findAllArticles(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveArticle(@RequestBody Article article){
        return new ResponseEntity<>(articleService.saveArticle(article), HttpStatus.CREATED);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @RequestBody Article article){
        return new ResponseEntity<>(articleService.saveArticle(article), HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId){
        articleService.deleteArticle(articleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
