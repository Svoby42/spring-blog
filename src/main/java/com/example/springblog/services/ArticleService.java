package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.User;
import com.example.springblog.repositories.ArticleRepository;
import com.example.springblog.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).get();

        article.setCreateTime(LocalDateTime.now());
        article.setUser(user);
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findAllArticlesOfUser(Long userId) {
        return articleRepository.findAllByUser_Id(userId);
    }

    @Override
    public List<Article> findAllArticlesOfSignedInUser() {
        User user = authenticationService.getSignedInUser();

        return articleRepository.findByUser_Username(user.getUsername());
    }
}
