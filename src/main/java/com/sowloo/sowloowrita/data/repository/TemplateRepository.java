package com.sowloo.sowloowrita.data.repository;

import com.sowloo.sowloowrita.data.models.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

}
