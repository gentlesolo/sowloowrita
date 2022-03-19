package com.sowloo.sowloowrita.data.repository;

import com.sowloo.sowloowrita.data.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
