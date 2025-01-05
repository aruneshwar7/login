package com.auth.vibe.login.controller;

import com.auth.vibe.login.model.InventoryModel;
import com.auth.vibe.login.payload.InventoryRequestDTO;
import com.auth.vibe.login.payload.InventoryResponseDTO;
import com.auth.vibe.login.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {
    @Autowired
    InventoryService inventoryService;


    @GetMapping("/api/admin/inventory/product")
    public ResponseEntity<InventoryResponseDTO> getProduct(){
      InventoryResponseDTO inventoryResponseDTO = inventoryService.getProduct();
        return ResponseEntity.ok(inventoryResponseDTO);
    }
    @PostMapping("/api/admin/inventory/product")
    public ResponseEntity<InventoryModel> addProduct(@RequestBody @Valid InventoryRequestDTO productModel){
        InventoryModel product = inventoryService.addProduct(productModel);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/api/admin/inventory/product/{id}")
    public ResponseEntity<InventoryModel> updateProduct(@PathVariable Long id, @RequestBody @Valid InventoryModel inventoryModel){
        InventoryModel product = inventoryService.updateProduct(id, inventoryModel);
        return ResponseEntity.ok(product);
    }
    @DeleteMapping("/api/admin/inventory/product/{id}")
    public ResponseEntity<InventoryModel> deleteProduct(@PathVariable Long id){
        InventoryModel product = inventoryService.deleteProduct(id);
        return ResponseEntity.ok(product);
    }
}
