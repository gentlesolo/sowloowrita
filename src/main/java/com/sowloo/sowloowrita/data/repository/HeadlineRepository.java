package com.sowloo.sowloowrita.data.repository;

import com.sowloo.sowloowrita.data.models.Headline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeadlineRepository extends JpaRepository<Headline, Long> {
    boolean existsByName(String name);

    Optional<Headline> findByName(String name);

    Optional<Headline> findByIndustry(String name);
}
