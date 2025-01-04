package com.auth.vibe.login.controller;

import com.auth.vibe.login.service.JsonService;
import com.auth.vibe.login.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class HomeController {
    @Autowired
    TemplateService templateService;

    @Autowired
    JsonService jsonService;
    @GetMapping("/api")
    public String home(){
        try {

            Map<String, Object> homeJson =  jsonService.getHomeJson();
            String templateString = templateService.render("home",homeJson);
            return templateString;
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
    }
}
