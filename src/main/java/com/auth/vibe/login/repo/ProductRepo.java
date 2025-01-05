package com.auth.vibe.login.repo;

import com.auth.vibe.login.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepo extends JpaRepository<ProductModel, Long> {
    Page<ProductModel> findByCategoryId(Long categoryId, Pageable pageable);
    Page<ProductModel> findByCategoryName(String categoryName, Pageable pageable);
    // Find products by genderName (through category)
    @Query("SELECT p FROM ProductModel p WHERE p.category.gender.name = :genderName")
    Page<ProductModel> findByGenderName(@Param("genderName") String genderName, Pageable pageable);
    @Query("SELECT p FROM ProductModel p WHERE p.category.gender.name = :genderName AND p.category.name = :categoryName")
    Page<ProductModel> findByGenderNameAndCategoryName(@Param("genderName") String genderName,
                                                       @Param("categoryName") String categoryName,
                                                       Pageable pageable);

}
