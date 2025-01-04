package com.auth.vibe.login.controller;


import com.auth.vibe.login.model.CategoryModel;
import com.auth.vibe.login.payload.CategoriesResponseDTO;
import com.auth.vibe.login.payload.CategoryDTO;
import com.auth.vibe.login.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/api/category")
    public ResponseEntity<CategoriesResponseDTO> getCategory() {
        CategoriesResponseDTO categoriesResponseDTO = categoryService.getCategories();
        return ResponseEntity.ok(categoriesResponseDTO);
    }

    @PostMapping("/api/admin/{gender_id}/category")
    public ResponseEntity<CategoryDTO> addCategory(@PathVariable Long gender_id,@RequestBody @Valid CategoryModel categoryModel) {
        CategoryDTO categoryDTO = categoryService.addCategory(gender_id,categoryModel);
        return ResponseEntity.ok(categoryDTO);
    }

    @PutMapping("/api/admin/category/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryModel categoryModel) {
        CategoryDTO categoryDTO = categoryService.updateCategory(id, categoryModel);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("/api/admin/category/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.deleteCategory(id);
        return ResponseEntity.ok(categoryDTO);
    }

}
