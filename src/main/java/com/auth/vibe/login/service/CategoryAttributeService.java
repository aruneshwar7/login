package com.auth.vibe.login.service;

import com.auth.vibe.login.exception.ApiExpection;
import com.auth.vibe.login.model.CategoryAttributeModel;
import com.auth.vibe.login.model.CategoryModel;
import com.auth.vibe.login.payload.CategoryAttributeResponseDTO;
import com.auth.vibe.login.repo.CategoryAttributeRepo;
import com.auth.vibe.login.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryAttributeService {
    @Autowired
    CategoryAttributeRepo categoryAttributeRepo;
    @Autowired
    CategoryRepo categoryRepo;

    public CategoryAttributeResponseDTO getCategoryAttribute(Long categoryId) {
        try {
            List<CategoryAttributeModel> categoryAttributeModels;
            if (categoryId != null) {
                // Find by categoryId only
                categoryAttributeModels = categoryAttributeRepo.findByCategoryId(categoryId);
            } else {
                categoryAttributeModels = categoryAttributeRepo.findAll();
            }
            return new CategoryAttributeResponseDTO(categoryAttributeModels);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("404");
        }
    }
    public CategoryAttributeModel addCategoryAttribute(Long categoryId,CategoryAttributeModel categoryAttributeModel) {
        try {
            CategoryModel existingCategoryModel = categoryRepo.findById(categoryId).orElseThrow(()->new ApiExpection("category not found"));
            categoryAttributeModel.setCategory(existingCategoryModel);
            CategoryAttributeModel addedCategoryAttribute = categoryAttributeRepo.save(categoryAttributeModel);
            return addedCategoryAttribute;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("404");
        }
    }
    public CategoryAttributeModel updateCategoryAttribute(Long id, CategoryAttributeModel categoryAttributeModel) {
        CategoryAttributeModel existingCategoryAttributeModel = categoryAttributeRepo.findById(id).orElseThrow(()->new ApiExpection("category attribute not found"));
        existingCategoryAttributeModel.setName(categoryAttributeModel.getName());
        CategoryAttributeModel updatedCategoryAttributeModel = categoryAttributeRepo.save(existingCategoryAttributeModel);
        return updatedCategoryAttributeModel;
    }
    public CategoryAttributeModel deleteCategoryAttribute(Long id) {
        CategoryAttributeModel existingCategoryAttributeModel = categoryAttributeRepo.findById(id).orElseThrow(()->new ApiExpection("category attribute not found"));
        categoryAttributeRepo.delete(existingCategoryAttributeModel);
        return existingCategoryAttributeModel;
    }
}
