package com.example.springblog.repositories;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
    void deleteByTitle(String title);

}
