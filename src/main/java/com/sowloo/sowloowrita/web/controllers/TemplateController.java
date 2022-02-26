package com.sowloo.sowloowrita.web.controllers;

import com.sowloo.sowloowrita.data.models.Template;
import com.sowloo.sowloowrita.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @PostMapping("/add")
    public String add(@RequestBody Template template){
        templateService.saveTemplate(template);
        return "new student added";
    }
}
