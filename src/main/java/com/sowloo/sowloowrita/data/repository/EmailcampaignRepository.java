package com.sowloo.sowloowrita.data.repository;

import com.sowloo.sowloowrita.data.models.Emailcampaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailcampaignRepository extends JpaRepository<Emailcampaign, Long> {
    boolean existsByName(String name);

    Optional<Emailcampaign> findByName(String name);

    Optional<Emailcampaign> findByIndustry(String industry);
}
