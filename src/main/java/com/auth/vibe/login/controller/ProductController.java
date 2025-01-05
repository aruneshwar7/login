package com.auth.vibe.login.controller;

import com.auth.vibe.login.config.Appconstant;
import com.auth.vibe.login.payload.ProductHtmlResponseDTO;
import com.auth.vibe.login.payload.ProductRequestDTO;
import com.auth.vibe.login.payload.ProductResponseDTO;
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
public class ProductController {

    @Autowired
    TemplateService templateService;
    @Autowired
    JsonService jsonService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProductService productService;
    @GetMapping("/api/product_json")
    public ResponseEntity<ProductResponseDTO> getProduct(
            @RequestParam(value = "pageNumber", defaultValue = Appconstant.categoryPageNumber) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Appconstant.categoryPageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = Appconstant.categorySortBy) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = Appconstant.categorySortOrder) String sortOrder
    ){
        ProductResponseDTO productResponseDTO = productService.getProduct(pageNumber, pageSize,sortBy, sortOrder);
        return ResponseEntity.ok(productResponseDTO);
    }
    @PostMapping("/api/admin/product")
    public ResponseEntity<ProductRequestDTO> addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO){
        ProductRequestDTO addedProduct  = productService.addProduct(productRequestDTO);
        return ResponseEntity.ok(addedProduct);
    }
    @PutMapping("/api/admin/product/{id}")
    public ResponseEntity<ProductRequestDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO productRequestDTO){
        ProductRequestDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/api/admin/product/{id}")
    public ResponseEntity<ProductRequestDTO> deleteProduct(@PathVariable Long id){
        ProductRequestDTO deletedProduct = productService.deleteProduct(id);
        return ResponseEntity.ok(deletedProduct);
    }



    @GetMapping("/api/products")
    public String getProductHtml(
            @RequestParam(value = "pageNumber", defaultValue = Appconstant.categoryPageNumber) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Appconstant.categoryPageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = Appconstant.categorySortBy) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = Appconstant.categorySortOrder) String sortOrder
    ){
        try {
            ProductResponseDTO productResponseDTO = productService.getProduct(pageNumber, pageSize,sortBy, sortOrder);
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
