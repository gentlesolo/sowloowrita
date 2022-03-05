package com.sowloo.sowloowrita.data.dto;

import com.sowloo.sowloowrita.data.models.AppUser;
import com.sowloo.sowloowrita.data.models.TemplateCat;
import lombok.Data;

@Data
public class TemplateDto {
    private Long id;
    private String title;
    private String description;
    private AppUser appuser;
    private TemplateCat category;

}