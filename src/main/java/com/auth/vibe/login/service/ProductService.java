package com.auth.vibe.login.service;

import com.auth.vibe.login.exception.ApiExpection;
import com.auth.vibe.login.model.CategoryModel;
import com.auth.vibe.login.model.InventoryModel;
import com.auth.vibe.login.model.ProductModel;
import com.auth.vibe.login.model.StickerModel;
import com.auth.vibe.login.payload.ProductHtmlResponseDTO;
import com.auth.vibe.login.payload.ProductRequestDTO;
import com.auth.vibe.login.payload.ProductResponseDTO;
import com.auth.vibe.login.repo.CategoryRepo;
import com.auth.vibe.login.repo.InventoryRepo;
import com.auth.vibe.login.repo.ProductRepo;
import com.auth.vibe.login.repo.StickerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    InventoryRepo inventoryRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    StickerRepo stickerRepo;
    @Autowired
    TemplateService templateService;


    public ProductResponseDTO getProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder ) {
        try {
            Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
            Page<ProductModel> pageContent;

            pageContent = productRepo.findAll(pageDetails);

            List productList = pageContent.getContent();
            if (productList.isEmpty()) {
                throw new ApiExpection("category was empty");
            }
            ProductResponseDTO productResponseDTO = setPageDetails(pageContent);
            productResponseDTO.setContent(productList);

            return productResponseDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("404");
        }
    }

    public ProductResponseDTO getProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder ,String gender_name) {
        try {
            Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
            Page<ProductModel> pageContent;

            pageContent = productRepo.findByGenderName(gender_name,pageDetails);
            List productList = pageContent.getContent();
//            if (productList.isEmpty()) {
//                throw new ApiExpection("category was empty");
//            }
            ProductResponseDTO productResponseDTO = setPageDetails(pageContent);
            productResponseDTO.setContent(productList);
            return productResponseDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("404");
        }
    }


    public ProductResponseDTO getProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder ,String gender_name,String category_name) {
        try {
            Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
            Page<ProductModel> pageContent;

            pageContent = productRepo.findByGenderNameAndCategoryName(gender_name,category_name,pageDetails);
            List productList = pageContent.getContent();
//            if (productList.isEmpty()) {
//                throw new ApiExpection("category was empty");
//            }
            ProductResponseDTO productResponseDTO = setPageDetails(pageContent);
            productResponseDTO.setContent(productList);
            return productResponseDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("404");
        }
    }


    public ProductRequestDTO addProduct(ProductRequestDTO productRequestDTO) {
        try {
            InventoryModel inventoryModel = inventoryRepo.findById(productRequestDTO.getInventoryId()).orElseThrow(() -> new ApiExpection("inventory not found"));
            CategoryModel categoryModel = categoryRepo.findById(productRequestDTO.getCategoryId()).orElseThrow(() -> new ApiExpection("category not found"));
            StickerModel stickerModel = stickerRepo.findById(productRequestDTO.getStickerId()).orElseThrow(() -> new ApiExpection("sticker not found"));
            ProductModel productModel = modelMapper.map(productRequestDTO, ProductModel.class);
            productModel.setInventory(inventoryModel);
            productModel.setCategory(categoryModel);
            productModel.setSticker(stickerModel);
            ProductModel addedProduct = productRepo.save(productModel);
            ProductRequestDTO addedProductRequestDTO = modelMapper.map(addedProduct, ProductRequestDTO.class);
            return addedProductRequestDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiExpection("bad credentials");
        }

    }
    public ProductRequestDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        try {
            ProductModel existingProductModel = productRepo.findById(id).orElseThrow(()->new ApiExpection("product not found"));
            existingProductModel.setName(productRequestDTO.getName());
            existingProductModel.setImageUrl(productRequestDTO.getImageUrl());
            ProductModel updatedProductModel = productRepo.save(existingProductModel);
            ProductRequestDTO updatedProductRequestDTO = modelMapper.map(updatedProductModel, ProductRequestDTO.class);
            return updatedProductRequestDTO;
        } catch (Exception e) {
            throw new ApiExpection("bad credentials");
        }
    }
    public ProductRequestDTO deleteProduct(Long id) {
        try {
            ProductModel existingProductModel = productRepo.findById(id).orElseThrow(() -> new ApiExpection("product not found"));
            productRepo.delete(existingProductModel);
            ProductRequestDTO deletedProductRequestDTO = modelMapper.map(existingProductModel, ProductRequestDTO.class);
            return deletedProductRequestDTO;
        } catch (Exception e) {
            throw new ApiExpection("bad credentials");
        }
    }

    public ProductHtmlResponseDTO getProductHtml(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        try {
            ProductResponseDTO productResponseDTO = getProduct(pageNumber, pageSize, sortBy, sortOrder);
            Map<String,Object> map = new HashMap<>();
            map.put("products",modelMapper.map(productResponseDTO, Object.class));
            String templateString = templateService.render("productsdiv",map);
            ProductHtmlResponseDTO productHtmlResponseDTO = new ProductHtmlResponseDTO(templateString,productResponseDTO.getPageNumber(),productResponseDTO.getPageSize(),productResponseDTO.getTotalPages(),productResponseDTO.getTotalElements(),productResponseDTO.getLastPage());
            return productHtmlResponseDTO;
        } catch (Exception e) {
            throw new ApiExpection("404");
        }
    }

    private ProductResponseDTO setPageDetails(Page<ProductModel> pageContent) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setPageNumber(pageContent.getNumber());
        productResponseDTO.setPageSize(pageContent.getSize());
        productResponseDTO.setTotalPages(pageContent.getTotalPages());
        productResponseDTO.setLastPage(pageContent.isLast());
        productResponseDTO.setTotalElements(pageContent.getTotalElements());
        return productResponseDTO;
    }
}
