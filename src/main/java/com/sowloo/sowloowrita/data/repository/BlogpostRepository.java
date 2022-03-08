package com.sowloo.sowloowrita.data.repository;

import com.sowloo.sowloowrita.data.models.Blogpost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogpostRepository extends JpaRepository<Blogpost, Long> {
    boolean existsByName(String name);

    Optional<Blogpost> findByName(String name);

    Optional<Blogpost> findByIndustry(String industry);
}
