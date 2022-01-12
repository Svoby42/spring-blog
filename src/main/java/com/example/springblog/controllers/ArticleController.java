package com.example.springblog.controllers;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.Role;
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

    private final IArticleService articleService;

    private final IAuthenticationService authenticationService;

    public ArticleController(IArticleService articleService, IAuthenticationService authenticationService) {
        this.articleService = articleService;
        this.authenticationService = authenticationService;
    }

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
        Optional<Article> foundArticleTitle = articleService.findArticleByTitle(article.getTitle());
        Optional<Article> foundArticleSlug = articleService.findArticleBySlug(article.getSlug());
        if(foundArticleTitle.isPresent() || foundArticleSlug.isPresent()){
            return new ResponseEntity<>("Article with the title/slug already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(articleService.saveArticle(article), HttpStatus.CREATED);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<?> updateArticle(@PathVariable String slug, @RequestBody Article article){
        User signedInUser = authenticationService.getSignedInUser();
        User articleOwner = articleService.findArticleBySlug(slug).get().getUser();

        if(signedInUser.getUsername().equals(articleOwner.getUsername()) || signedInUser.getRole().name().equals(Role.ADMIN.name())){
            Optional<Article> original = articleService.findArticleBySlug(slug);
            if(original.isPresent()){
                article.setSlug(slug);
                article.setUser(articleOwner);
                return new ResponseEntity<>(articleService.updateArticle(article), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<?> deleteArticle(@PathVariable String slug){
        User signedInUser = authenticationService.getSignedInUser();
        User articleAuthor = articleService.findAuthorOfArticle(slug);

        if(signedInUser.getId().equals(articleAuthor.getId()) || signedInUser.getRole().equals(Role.ADMIN) ){
            articleService.deleteArticle(slug);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Article is not yours!", HttpStatus.UNAUTHORIZED);
    }


}
