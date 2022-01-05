package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.User;

import java.util.List;

public interface IArticleService {

    Article saveArticle(Article article);

    void deleteArticle(Long id);

    List<Article> findAllArticles();

    List<Article> findAllArticlesOfUser(Long userId);

    List<Article> findAllArticlesOfSignedInUser();

}
