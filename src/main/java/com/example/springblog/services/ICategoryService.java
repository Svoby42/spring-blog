package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    Optional<Category> findCategoryById(Long id);

    Optional<Category> findCategoryByTitle(String title);

    void deleteCategory(String title);

    Category saveCategory(Category category);

    List<Category> findAllCategories();

    List<Article> findAllArticlesOfCategory(String title);

}
