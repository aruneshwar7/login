package com.auth.vibe.login.service;

import com.auth.vibe.login.exception.ApiExpection;
import com.auth.vibe.login.model.CategoryModel;
import com.auth.vibe.login.model.GenderModel;
import com.auth.vibe.login.payload.CategoriesResponseDTO;
import com.auth.vibe.login.payload.CategoryDTO;
import com.auth.vibe.login.repo.CategoryRepo;
import com.auth.vibe.login.repo.GenderRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    GenderRepo genderRepo;

    public CategoriesResponseDTO getCategories() {

        List<CategoryModel> categoryModels = categoryRepo.findAll();
        return new CategoriesResponseDTO(categoryModels);
    }
    public CategoryDTO addCategory(Long gender_id,CategoryModel categoryModel) {

        try {
            GenderModel existingGenderModel = genderRepo.findById(gender_id).orElseThrow(()->new ApiExpection("gender not found"));
            categoryModel.setGender(existingGenderModel);
            CategoryModel addedCategory = categoryRepo.save(categoryModel);
            return modelMapper.map(addedCategory, CategoryDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("404");
        }
    }
    public CategoryDTO updateCategory(Long id, CategoryModel categoryModel) {

        try{
            CategoryModel existingCategoryModel = categoryRepo.findById(id).orElseThrow(()->new ApiExpection("category not found"));
            existingCategoryModel.setName(categoryModel.getName());
            CategoryModel  updatedCategoryModel = categoryRepo.save(existingCategoryModel);
            return modelMapper.map(updatedCategoryModel, CategoryDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("404");
        }
    }
    public CategoryDTO deleteCategory(Long id) {

        try {
            CategoryModel existing = categoryRepo.findById(id).orElseThrow(() -> new ApiExpection("category not found"));
            categoryRepo.delete(existing);
            return modelMapper.map(existing, CategoryDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("404");
        }
    }
}
