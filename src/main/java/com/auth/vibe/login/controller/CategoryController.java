package com.auth.vibe.login.controller;


import com.auth.vibe.login.config.Appconstant;
import com.auth.vibe.login.model.CategoryModel;
import com.auth.vibe.login.payload.CategoriesResponseDTO;
import com.auth.vibe.login.payload.CategoryDTO;
import com.auth.vibe.login.payload.ProductResponseDTO;
import com.auth.vibe.login.service.CategoryService;
import com.auth.vibe.login.service.JsonService;
import com.auth.vibe.login.service.ProductService;
import com.auth.vibe.login.service.TemplateService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    JsonService jsonService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TemplateService templateService;



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

    @GetMapping("/api/products/{gender_name}/{category_name}")
    public String getProductHtml(
            @RequestParam(value = "pageNumber", defaultValue = Appconstant.categoryPageNumber) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Appconstant.categoryPageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = Appconstant.categorySortBy) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = Appconstant.categorySortOrder) String sortOrder,
            @PathVariable(value = "gender_name") String gender_name,
            @PathVariable(value = "category_name") String category_name
    ){
        try {
            ProductResponseDTO productResponseDTO = productService.getProduct(pageNumber, pageSize,sortBy, sortOrder, gender_name,category_name);
            Map<String, Object> productJson =  jsonService.getProductJson();
            productJson.put("products",modelMapper.map(productResponseDTO, Object.class));
            String templateString = templateService.render("products",productJson);
            return templateString;
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
    }

}
