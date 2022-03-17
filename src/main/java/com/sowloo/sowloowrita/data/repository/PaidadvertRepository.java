package com.sowloo.sowloowrita.data.repository;

import com.sowloo.sowloowrita.data.models.Paidadvert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaidadvertRepository extends JpaRepository<Paidadvert, Long> {
    boolean existsByName(String name);

    Optional<Paidadvert> findByName(String name);

    Optional<Paidadvert> findByIndustry(String industry);
}
