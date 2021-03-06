package com.example.springblog.repositories;

import com.example.springblog.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    void deleteBySlug(String slug);
    List<Article> findAllByUser_Username(String username);          //find all articles by username
    List<Article> findAllByUser_Id(Long userId);                    //find all articles by user id
    List<Article> findAllByCategory_Title(String categoryTitle);    //find all articles of a category
    List<Article> findByUser_Username(String username);             //find only one article by username
    Optional<Article> findByTitle(String title);
    Optional<Article> findBySlug(String slug);

}
