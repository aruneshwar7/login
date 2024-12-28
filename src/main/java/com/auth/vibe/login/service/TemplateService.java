package com.auth.vibe.login.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class TemplateService {

    @Autowired
    private final Handlebars handlebars;

    public TemplateService(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    public String render(String templateName, Map<String, Object> model) throws IOException, IOException {
        Template template = handlebars.compile(templateName);
        return template.apply(model);
    }
}
