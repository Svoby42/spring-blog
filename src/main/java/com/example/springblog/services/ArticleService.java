package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.User;
import com.example.springblog.repositories.ArticleRepository;
import com.example.springblog.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Article editedArticle = articleRepository.findById(article.getId()).get();
        editedArticle.setTitle(article.getTitle());
        editedArticle.setContent(article.getContent());
        return articleRepository.save(editedArticle);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void deleteAllArticlesOfUser(String username){
        this.findAllArticlesOfUser(username).forEach(article -> this.deleteArticle(article.getId()));
    }

    @Override
    public Optional<Article> findArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Optional<Article> findArticleByTitle(String title){
        return articleRepository.findByTitle(title);
    }

    @Override
    public User findAuthorOfArticle(Long id) {
        return articleRepository.findById(id).get().getUser();
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
