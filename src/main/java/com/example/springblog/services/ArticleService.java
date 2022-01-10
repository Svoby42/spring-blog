package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.User;
import com.example.springblog.repositories.ArticleRepository;
import com.example.springblog.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService implements IArticleService{

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, AuthenticationService authenticationService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }


    @Override
    public Article saveArticle(Article article) {
        User user = this.authenticationService.getSignedInUser();
        article.setCreate_time(LocalDateTime.now());
        article.setAuthor_name(user.getName());
        article.setUser(user);
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Article article) {
        Article editedArticle = articleRepository.findBySlug(article.getSlug()).get();
        editedArticle.setTitle(article.getTitle());
        editedArticle.setContent(article.getContent());
        editedArticle.setEdit_time(LocalDateTime.now());
        return articleRepository.save(editedArticle);
    }

    @Override
    public void deleteArticle(String id) {
        articleRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public void deleteAllArticlesOfUser(String username){
        this.findAllArticlesOfUser(username).forEach(article -> this.deleteArticle(article.getId()));
    }

    @Override
    public Optional<Article> findArticleById(String id) {
        return articleRepository.findById(UUID.fromString(id));
    }

    @Override
    public Optional<Article> findArticleByTitle(String title){
        return articleRepository.findByTitle(title);
    }

    @Override
    public Optional<Article> findArticleBySlug(String slug) {
        return articleRepository.findBySlug(slug);
    }

    @Override
    public User findAuthorOfArticle(String id) {
        return articleRepository.findById(UUID.fromString(id)).get().getUser();
    }

    @Override
    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findAllArticlesOfUser(String username) {
        return articleRepository.findAllByUser_Username(username);
    }

    @Override
    public List<Article> findAllArticlesOfCategory(String categoryTitle) {
        return articleRepository.findAllByCategory_Title(categoryTitle);
    }

    @Override
    public List<Article> findAllArticlesOfSignedInUser() {
        User user = authenticationService.getSignedInUser();

        return articleRepository.findByUser_Username(user.getUsername());
    }
}
