package com.sowloo.sowloowrita.data.repository;

import com.sowloo.sowloowrita.data.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findById(String id);

    Boolean existsByUserName(String userName);

    Boolean existsByEmailId(String emailId);

}
