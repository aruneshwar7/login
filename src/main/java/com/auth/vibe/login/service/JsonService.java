package com.auth.vibe.login.service;

import com.auth.vibe.login.config.Appconstant;
import com.auth.vibe.login.payload.CategoriesResponseDTO;
import com.auth.vibe.login.payload.ProductResponseDTO;
import com.auth.vibe.login.utils.JsonFileReader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JsonService {
    @Autowired
    CategoryService categoryService;
    @Autowired
    GenderService genderService;
    @Autowired
    JsonFileReader jsonFileReader;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductService productService;
    public Map<String, Object> getHomeJson() throws Exception {
        Map<String, Object> homePageJson = JsonFileReader.readJson("home.json");
        homePageJson.put("navbar", getNavbarJson());
        homePageJson.put("products",getDefaultProductJson());
        homePageJson.put("categories", genderService.getGender().getContent());
        return homePageJson;
    }

    public Map<String,Object> getProductJson() throws Exception {
        Map<String, Object> productPageJson = JsonFileReader.readJson("products.json");
        productPageJson.put("navbar", getNavbarJson());
        productPageJson.put("products", getDefaultProductJson());
        return productPageJson;
    }

    private Object getDefaultProductJson(){
        ProductResponseDTO productResponseDTO = productService.getProduct(Integer.parseInt( Appconstant.categoryPageNumber), Integer.parseInt( Appconstant.categoryPageSize), Appconstant.categorySortBy, Appconstant.categorySortOrder);
        return modelMapper.map(productResponseDTO, Object.class);
    }


    private Map<String, Object> getNavbarJson(){
        Map<String, Object> navbarJson = JsonFileReader.readJson("component/navbar.json");
        Map<String, Object> navbar = (Map<String, Object>) navbarJson.get("navbar");
        navbar.put("categories", genderService.getGender().getContent());
        return navbar;
    }




}
