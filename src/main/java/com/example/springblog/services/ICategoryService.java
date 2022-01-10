package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoryService {

    Optional<Category> findCategoryById(String id);

    Optional<Category> findCategoryBySlug(String slug);

    void deleteCategory(String title);

    Category saveCategory(Category category);

    Category updateCategory(Category category);

    List<Category> findAllCategories();

    List<Article> findAllArticlesOfCategory(String title);

}
