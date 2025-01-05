package com.auth.vibe.login.service;

import com.auth.vibe.login.exception.ApiExpection;
import com.auth.vibe.login.model.CategoryModel;
import com.auth.vibe.login.model.InventoryModel;
import com.auth.vibe.login.payload.InventoryRequestDTO;
import com.auth.vibe.login.payload.InventoryResponseDTO;
import com.auth.vibe.login.repo.CategoryRepo;
import com.auth.vibe.login.repo.InventoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    InventoryRepo inventoryRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;
    public InventoryResponseDTO getProduct(){
        try {
            List<InventoryModel> productList = inventoryRepo.findAll();
            return new InventoryResponseDTO(productList);
        } catch (Exception e) {
            throw new ApiExpection("bad credentials");
        }
    }
    public InventoryModel addProduct(InventoryRequestDTO productModelDTO){
        try {
            CategoryModel category = categoryRepo.findById(productModelDTO.getCategoryId()).orElseThrow(()->new ApiExpection("category not found"));
           InventoryModel inventoryModel = modelMapper.map(productModelDTO, InventoryModel.class);
            inventoryModel.setCategory(category);
            InventoryModel addedProduct = inventoryRepo.save(inventoryModel);
            return addedProduct;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("bad credentials");
        }
    }
    public InventoryModel updateProduct(Long id, InventoryModel inventoryModel) {

        try {
            InventoryModel existingProduct = inventoryRepo.findById(id).orElseThrow(()->new ApiExpection("product not found"));
            if (inventoryModel.getName() != null)
                existingProduct.setName(inventoryModel.getName());
            if (inventoryModel.getImageUrl() != null)
                existingProduct.setImageUrl(inventoryModel.getImageUrl());
            InventoryModel updatedProduct = inventoryRepo.save(existingProduct);
            return updatedProduct;
        } catch (Exception e) {
            throw new ApiExpection("bad credentials");
        }
    }
    public InventoryModel deleteProduct(Long id) {
        try {
            InventoryModel existing = inventoryRepo.findById(id).orElseThrow(() -> new ApiExpection("product not found"));
            inventoryRepo.delete(existing);
            return existing;
        } catch (Exception e) {
            throw new ApiExpection("bad credentials");
        }
    }
}
