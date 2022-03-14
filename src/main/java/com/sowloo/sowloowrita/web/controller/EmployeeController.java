package com.sowloo.sowloowrita.web.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.sowloo.sowloowrita.data.dtos.request.CreateEmployeeRequestDto;
import com.sowloo.sowloowrita.data.dtos.response.ApiResponse;
import com.sowloo.sowloowrita.data.dtos.response.CreateEmployeeResponseDto;
import com.sowloo.sowloowrita.data.models.Employee;
import com.sowloo.sowloowrita.data.repository.EmployeeRepository;
import com.sowloo.sowloowrita.service.employee.EmployeeService;
import com.sowloo.sowloowrita.web.exceptions.DuplicateEmailException;
import com.sowloo.sowloowrita.web.exceptions.EmployeeNotFoundException;
import com.sowloo.sowloowrita.web.exceptions.RunTimeExceptionPlaceholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/api/v1/employee")
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping()
    public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeRequestDto createEmployeeRequest) {

        try {
               CreateEmployeeResponseDto responseDto =  employeeService.createEmployee(createEmployeeRequest);
                return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (DuplicateEmailException | RunTimeExceptionPlaceholder e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/api/v1/employees")
//    public List<Employee> getAllEmployees() {
//        return employeeService.getAllEmployees();
//    }

    @GetMapping()
    public ResponseEntity<?> getAllEmployees(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", required = false, defaultValue = "30") int pageSize,
                                             @RequestParam(name = "sort", required = false, defaultValue = "dateCreated") String sortTerm){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortTerm).descending());
        List<Employee> employees = employeeService.getAllEmployees(pageable);
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>findByEmployeeId(@PathVariable String id){
        try{
            return new ResponseEntity<>(employeeService.findByEmployeeId(id), HttpStatus.OK);
        }catch (EmployeeNotFoundException e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody Employee employeeDetails){
      try {
          return new ResponseEntity<>(employeeService.updateEmployee(id,employeeDetails), HttpStatus.OK);
      }catch (EmployeeNotFoundException e){
          return new ResponseEntity<>(new ApiResponse(false, "Employee Update Failed"), HttpStatus.BAD_REQUEST);
      }
    }


     @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteEmployee(@PathVariable String id){

        try {
             employeeService.deleteEmployeeById(id);
            return new ResponseEntity<>(new ApiResponse(true, "Employee Deleted Successfully"), HttpStatus.NO_CONTENT);
        }catch(EmployeeNotFoundException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PatchMapping(path = "/{employeeId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateEmployeeDetails(@PathVariable String employeeId, @RequestBody JsonPatch patch){

        try {
            Employee updatedEmployee = employeeService.updateEmployeeDetails(employeeId, patch);
            return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
        }catch (EmployeeNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


/*
* presentation layer
* business logic
* data access loose coupling
* */