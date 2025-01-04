package com.auth.vibe.login.repo;

import com.auth.vibe.login.model.CategoryAttributeModel;
import com.auth.vibe.login.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryAttributeRepo extends JpaRepository<CategoryAttributeModel, Long> {

    List<CategoryAttributeModel> findByCategoryId(Long categoryId);


}

