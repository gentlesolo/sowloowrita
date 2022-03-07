package com.sowloo.sowloowrita.service;

import com.sowloo.sowloowrita.data.models.Template;
import com.sowloo.sowloowrita.data.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService{

    @Autowired
    private TemplateRepository templateRepository;

    @Override
    public Template saveTemplate(Template template) {
        return templateRepository.save(template);
    }
}
