package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.Category;
import com.example.springblog.repositories.ArticleRepository;
import com.example.springblog.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService{

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public CategoryService(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findCategoryById(String id) {
        return categoryRepository.findById(UUID.fromString(id));
    }

    @Override
    public Optional<Category> findCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    @Override
    public void deleteCategory(String slug) {
        categoryRepository.deleteBySlug(slug);
    }

    @Override
    public Category saveCategory(Category category) {
        category.setCreate_time(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category originalCategory = categoryRepository.findBySlug(category.getSlug()).get();
        category.setSlug(originalCategory.getSlug());
        category.setId(originalCategory.getId());
        category.setCreate_time(originalCategory.getCreate_time());
        category.setArticle_list(originalCategory.getArticle_list());

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Article> findAllArticlesOfCategory(String title) {
        return articleRepository.findAllByCategory_Title(title);
    }
}
