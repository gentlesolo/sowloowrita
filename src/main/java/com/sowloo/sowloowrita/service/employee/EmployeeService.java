package com.sowloo.sowloowrita.service.employee;

import com.sowloo.sowloowrita.data.dtos.request.CreateEmployeeRequestDto;
import com.sowloo.sowloowrita.data.dtos.response.CreateEmployeeResponseDto;
import com.sowloo.sowloowrita.data.models.Employee;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;


public interface EmployeeService {

    CreateEmployeeResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequest);

    List<Employee> getAllEmployees();

    Employee findByEmployeeId(String id);

    Employee updateEmployee(String id, Employee employeeDetails);

    Employee updateEmployeeDetails(String employeeId, JsonPatch patch);

    void deleteEmployeeById(String employeeId);

}
