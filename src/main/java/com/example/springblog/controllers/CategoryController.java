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

    @GetMapping("/{title}")
    public ResponseEntity<?> getCategory(@PathVariable String title){
        return new ResponseEntity<>(categoryService.findCategoryByTitle(title), HttpStatus.OK);
    }



    @PutMapping("/{title}")
    public ResponseEntity<?> updateCategory(@PathVariable String title, @RequestBody Category category){
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.OK);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<?> deleteCategory(@PathVariable String title){
        categoryService.deleteCategory(title);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
