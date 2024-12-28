package com.auth.vibe.login.config;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public Handlebars handlebars() {
        ClassPathTemplateLoader loader = new ClassPathTemplateLoader("/templates/pages", ".hbs");
        return new Handlebars(loader);
    }
}
