package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IArticleService {

    Optional<Article> findArticleById(UUID id);

    Optional<Article> findArticleByTitle(String title);

    Optional<Article> findArticleBySlug(String slug);

    User findAuthorOfArticle(String articleId);

    Article saveArticle(Article article);

    Article updateArticle(Article article);

    void deleteArticle(String id);

    void deleteAllArticlesOfUser(String username);

    List<Article> findAllArticles();

    List<Article> findAllArticlesOfUser(String username);

    List<Article> findAllArticlesOfCategory(String categoryTitle);

    List<Article> findAllArticlesOfSignedInUser();

}
