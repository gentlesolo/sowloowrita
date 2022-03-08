package com.sowloo.sowloowrita.data.repository;

import com.sowloo.sowloowrita.data.models.Socialmedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialmediaRepository extends JpaRepository<Socialmedia, Long> {
    boolean existsByName(String name);

    Optional<Socialmedia> findByName(String name);

    Optional<Socialmedia> findByIndustry(String industry);
}
