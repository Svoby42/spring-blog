package com.example.springblog.repositories;

import com.example.springblog.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByUser_Id(Long userId);

    List<Article> findByUser_Username(String username);

    Optional<Article> findByTitle(String title);

}
