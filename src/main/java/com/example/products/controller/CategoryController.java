package com.example.products.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.products.model.Category;
import com.example.products.repository.CategoryRepository;

@RestController 
@RequestMapping ("/categories")
public class CategoryController {

    private final CategoryRepository repoCategory;
    public CategoryController(CategoryRepository repoCategory){
        this.repoCategory = repoCategory;
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        return ResponseEntity.ok(
            repoCategory.save(category)
        );
    }

    @GetMapping 
    public ResponseEntity<List<Category>> getALl() {
        return ResponseEntity.ok(
            repoCategory.findAll()
        );
    }
}
