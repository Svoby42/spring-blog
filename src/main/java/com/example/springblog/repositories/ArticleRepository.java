package com.example.springblog.repositories;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.Role;
import com.example.springblog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByUser_Id(Long userId);

    List<Article> findByUser_Username(String username);

}
