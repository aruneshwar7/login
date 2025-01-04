package com.auth.vibe.login.service;

import com.auth.vibe.login.utils.JsonFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JsonService {

    @Autowired
    GenderService genderService;
    @Autowired
    JsonFileReader jsonFileReader;
    public Map<String, Object> getHomeJson() throws Exception {
        Map<String, Object> homePageJson = JsonFileReader.readJson("home.json");
        Map<String, Object> navbar = (Map<String, Object>) homePageJson.get("navbar");
        navbar.put("categories", genderService.getGender().getContent());
        return homePageJson;
    }

}
