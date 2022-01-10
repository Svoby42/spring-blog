package com.example.springblog.controllers;

import com.example.springblog.entities.Category;
import com.example.springblog.services.IArticleService;
import com.example.springblog.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        return new ResponseEntity<>(categoryService.findAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getCategory(@PathVariable String slug){
        return new ResponseEntity<>(categoryService.findCategoryBySlug(slug), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<?> updateCategory(@PathVariable String slug, @RequestBody Category category){
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.OK);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<?> deleteCategory(@PathVariable String slug){
        categoryService.deleteCategory(slug);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
