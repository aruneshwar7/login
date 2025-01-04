package com.auth.vibe.login.repo;

import com.auth.vibe.login.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryModel, Long> {}
