package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.model.Category;
import com.joliest.portfolios.groceryapi.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Category> addMultipleCategories(@RequestBody List<Category> categories) {
        return categoryService.addMultipleCategories(categories);
    }
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@NotNull @PathVariable Integer categoryId) {
        categoryService.deleteCategoryById(categoryId);
    }
}
