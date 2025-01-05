package com.auth.vibe.login.controller;

import com.auth.vibe.login.config.Appconstant;
import com.auth.vibe.login.model.GenderModel;
import com.auth.vibe.login.payload.GenderRequestDTO;
import com.auth.vibe.login.payload.GenderResponseDTO;
import com.auth.vibe.login.payload.ProductResponseDTO;
import com.auth.vibe.login.service.GenderService;
import com.auth.vibe.login.service.JsonService;
import com.auth.vibe.login.service.ProductService;
import com.auth.vibe.login.service.TemplateService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GenderController {

    @Autowired
    GenderService genderService;
    @Autowired
    ProductService productService;
    @Autowired
    JsonService jsonService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TemplateService templateService;

    @GetMapping("/api/gender")
    public ResponseEntity<GenderResponseDTO> getGender() {

        GenderResponseDTO genderResponseDTO = genderService.getGender();
        return new ResponseEntity<>(genderResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/api/admin/gender")
    public ResponseEntity<GenderRequestDTO> addGender(@RequestBody @Valid  GenderModel genderModel) {
        GenderRequestDTO genderRequestDTO = genderService.addGender(genderModel);
        return new ResponseEntity<>(genderRequestDTO, HttpStatus.CREATED);
    }

    @PutMapping("/api/admin/gender/{id}")
    public ResponseEntity<GenderRequestDTO> updateGender(@PathVariable Long id, @RequestBody @Valid GenderModel genderModel) {
        GenderRequestDTO genderRequestDTO = genderService.updateGender(id, genderModel);
        return new ResponseEntity<>(genderRequestDTO, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/gender/{id}")
    public ResponseEntity<GenderRequestDTO> deleteGender(@PathVariable Long id) {
        GenderRequestDTO deletedGender  = genderService.deleteGender(id);
        return new ResponseEntity<>(deletedGender, HttpStatus.OK);
    }
    @GetMapping("/api/products/{gender_name}")
    public String getProductHtml(
            @RequestParam(value = "pageNumber", defaultValue = Appconstant.categoryPageNumber) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Appconstant.categoryPageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = Appconstant.categorySortBy) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = Appconstant.categorySortOrder) String sortOrder,
            @PathVariable(value = "gender_name") String gender_name
    ){
        try {
            ProductResponseDTO productResponseDTO = productService.getProduct(pageNumber, pageSize,sortBy, sortOrder, gender_name);
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
