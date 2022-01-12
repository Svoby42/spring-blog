package com.example.springblog.services;

import com.example.springblog.entities.Article;
import com.example.springblog.entities.Category;
import com.example.springblog.entities.User;
import com.example.springblog.repositories.ArticleRepository;
import com.example.springblog.repositories.CategoryRepository;
import com.example.springblog.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService implements IArticleService{

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final CategoryRepository categoryRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, AuthenticationService authenticationService, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Article saveArticle(Article article) {
        User user = this.authenticationService.getSignedInUser();
        Optional<Category> category = categoryRepository.findBySlug(article.getCategory_slug());

        if(category.isPresent()){
            category.get().getArticle_list().add(article);
            article.setCategory(category.get());
        }
        article.setCreate_time(LocalDateTime.now());
        article.setAuthor_name(user.getName());
        article.setUser(user);

        return articleRepository.save(article);
    }

    @Transactional
    @Override
    public Article updateArticle(Article article) {
        Article originalArticle = articleRepository.findBySlug(article.getSlug()).get();
        article.setId(originalArticle.getId());
        article.setAuthor_name(originalArticle.getAuthor_name());
        article.setCreate_time(originalArticle.getCreate_time());
        article.setEdit_time(LocalDateTime.now());

        if(article.getCategory_slug() == null){
            article.setCategory_slug(originalArticle.getCategory_slug());
        }

        Optional<Category> category = categoryRepository.findBySlug(article.getCategory_slug());
        if(category.isPresent()){
            article.setCategory(category.get());
        }
        if(article.getTitle() == null){
            article.setTitle(originalArticle.getTitle());
        }

        return articleRepository.save(article);
    }

    @Transactional
    @Override
    public void deleteArticle(String slug) {
        articleRepository.deleteBySlug(slug);
    }

    @Override
    public void deleteAllArticlesOfUser(String username){
        this.findAllArticlesOfUser(username).forEach(article -> this.deleteArticle(article.getId()));
    }

    @Override
    public Optional<Article> findArticleById(String id) {
        return articleRepository.findById(UUID.fromString(id));
    }

    @Override
    public Optional<Article> findArticleByTitle(String title){
        return articleRepository.findByTitle(title);
    }

    @Override
    public Optional<Article> findArticleBySlug(String slug) {
        return articleRepository.findBySlug(slug);
    }

    @Override
    public User findAuthorOfArticle(String slug) {
        return articleRepository.findBySlug(slug).get().getUser();
    }

    @Override
    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findAllArticlesOfUser(String username) {
        return articleRepository.findAllByUser_Username(username);
    }

    @Override
    public List<Article> findAllArticlesOfCategory(String categoryTitle) {
        return articleRepository.findAllByCategory_Title(categoryTitle);
    }

    @Override
    public List<Article> findAllArticlesOfSignedInUser() {
        User user = authenticationService.getSignedInUser();

        return articleRepository.findByUser_Username(user.getUsername());
    }
}
