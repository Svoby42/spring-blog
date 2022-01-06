package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.User;

import java.util.List;
import java.util.Optional;

public interface IArticleService {

    Optional<Article> findArticleByTitle(String title);

    Article saveArticle(Article article);

    void deleteArticle(Long id);

    List<Article> findAllArticles();

    List<Article> findAllArticlesOfUser(Long userId);

    List<Article> findAllArticlesOfSignedInUser();

}
