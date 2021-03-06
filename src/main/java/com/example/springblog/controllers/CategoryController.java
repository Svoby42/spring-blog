package com.example.springblog.controllers;

import com.example.springblog.entities.Category;
import com.example.springblog.services.IArticleService;
import com.example.springblog.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final IArticleService articleService;

    private final ICategoryService categoryService;

    public CategoryController(IArticleService articleService, ICategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        return new ResponseEntity<>(categoryService.findAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getCategory(@PathVariable String slug){
        Optional<Category> category = categoryService.findCategoryBySlug(slug);
        if(category.isPresent()){
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<?> updateCategory(@PathVariable String slug, @RequestBody Category category){
        Optional<Category> toBeEdited = categoryService.findCategoryBySlug(slug);
        if(toBeEdited.isPresent()){
            category.setSlug(slug);
            return new ResponseEntity<>(categoryService.updateCategory(category), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<?> deleteCategory(@PathVariable String slug){
        categoryService.deleteCategory(slug);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
