package com.sowloo.sowloowrita.data.repository;

import com.sowloo.sowloowrita.data.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String username);
}
