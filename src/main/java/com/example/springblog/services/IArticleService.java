package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.User;

import java.util.List;
import java.util.Optional;

public interface IArticleService {

    Optional<Article> findArticleById(Long id);

    Optional<Article> findArticleByTitle(String title);

    User findAuthorOfArticle(Long articleId);

    Article saveArticle(Article article);

    void deleteArticle(Long id);

    void deleteAllArticlesOfUser(String username);

    List<Article> findAllArticles();

    List<Article> findAllArticlesOfUser(String username);

    List<Article> findAllArticlesOfSignedInUser();

}
