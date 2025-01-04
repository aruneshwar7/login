package com.auth.vibe.login.controller;

import com.auth.vibe.login.model.CategoryAttributeModel;
import com.auth.vibe.login.payload.CategoriesResponseDTO;
import com.auth.vibe.login.payload.CategoryAttributeResponseDTO;
import com.auth.vibe.login.service.CategoryAttributeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryAttributeController {
    @Autowired
    private CategoryAttributeService categoryAttributeService;

    @GetMapping("/api/category-attribute")
    public ResponseEntity<CategoryAttributeResponseDTO> getCategoryAttribute(
            @RequestParam(value = "categoryId", required = false) Long categoryId
    ) {
        CategoryAttributeResponseDTO categoryAttributeResponseDTO = categoryAttributeService.getCategoryAttribute(categoryId);
        return ResponseEntity.ok(categoryAttributeResponseDTO);
    }
    @PostMapping("/api/admin/{category_id}/category-attribute")
    public ResponseEntity<CategoryAttributeModel> addCategoryAttribute(@PathVariable Long category_id ,@RequestBody @Valid CategoryAttributeModel categoryAttributeModel) {
        CategoryAttributeModel categoryAttributeModel1 = categoryAttributeService.addCategoryAttribute(category_id,categoryAttributeModel);
        return ResponseEntity.ok(categoryAttributeModel1);
    }
    @PutMapping("/api/admin/category-attribute/{id}")
    public ResponseEntity<CategoryAttributeModel> updateCategoryAttribute(@PathVariable Long id, @RequestBody @Valid CategoryAttributeModel categoryAttributeModel) {
        CategoryAttributeModel categoryAttributeModel1 = categoryAttributeService.updateCategoryAttribute(id, categoryAttributeModel);
        return ResponseEntity.ok(categoryAttributeModel1);
    }
    @DeleteMapping("/api/admin/category-attribute/{id}")
    public ResponseEntity<CategoryAttributeModel> deleteCategoryAttribute(@PathVariable Long id) {
        CategoryAttributeModel categoryAttributeModel = categoryAttributeService.deleteCategoryAttribute(id);
        return ResponseEntity.ok(categoryAttributeModel);
    }
}
