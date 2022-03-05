package com.sowloo.sowloowrita.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sowloo.sowloowrita.data.dtos.request.CreateEmployeeRequestDto;
import com.sowloo.sowloowrita.data.dtos.response.CreateEmployeeResponseDto;
import com.sowloo.sowloowrita.data.models.Employee;
import com.sowloo.sowloowrita.email.EmailSender;
import com.sowloo.sowloowrita.data.repository.EmployeeRepository;
//import com.sowloo.sowloowrita.service.cloud.CloudService;
import com.sowloo.sowloowrita.service.employee.EmployeeService;
import com.sowloo.sowloowrita.web.exceptions.DuplicateEmailException;
import com.sowloo.sowloowrita.web.exceptions.EmployeeNotFoundException;
import com.sowloo.sowloowrita.web.exceptions.RunTimeExceptionPlaceholder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {


//    @Autowired
//    private CloudService cloudService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public CreateEmployeeResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequest) {

        if (employeeRepository.existsByUserName(createEmployeeRequest.getUserName())) {
            throw new RunTimeExceptionPlaceholder("username already exist");
        }

        if (employeeRepository.existsByEmailId(createEmployeeRequest.getEmailId())) {
            throw new DuplicateEmailException("Email already exist");
        }

        Employee employee1 = Employee.builder()

                .firstName(createEmployeeRequest.getFirstName())
                .lastName(createEmployeeRequest.getLastName())
                .emailId(createEmployeeRequest.getEmailId())
                .userName(createEmployeeRequest.getUserName())
                .generateEmployeeId(generateEmployeeId())
                .build();

//        try{
//            if (createEmployeeRequest.getImage() != null){
//                Map<?,?> uploadImageResult = cloudService.upload(createEmployeeRequest.getImage().getBytes(),
//                        ObjectUtils.asMap("public_id",
//                                "inventory/" + createEmployeeRequest.getImage().getOriginalFilename(),
//                                "overwrite", true
//                        ));
//            employee1.setImageUrl(uploadImageResult.get("url").toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
                saveEmployee(employee1);


//        emailSender.send("oziichukwu1@gmail.com", "Employee Management System", "Welcome to XYZ firm ");
//        emailSender.send("oziichukwu1@gmail.com", "welcome ");

        return CreateEmployeeResponseDto.builder()
                .userName(employee1.getUserName())
                .id(employee1.getId())
                .generatedEmployeeId(generateEmployeeId())
                .build();

    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public Employee findByEmployeeId(String id) {

         Optional<Employee> userEmployee = employeeRepository.findById(id);
        return userEmployee.orElseThrow(()->
                new EmployeeNotFoundException("Employee does not exist"));
    }

    @Override
    public Employee updateEmployee(String id, Employee employeeDetails) {

        Employee updateEmployee = employeeRepository.findById(id).orElseThrow(()->
                new EmployeeNotFoundException("Employee does not exist"));

        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmailId(employeeDetails.getEmailId());
        updateEmployee.setUserName(employeeDetails.getUserName());

        return employeeRepository.save(updateEmployee);
    }

    private Employee saveEmployee(Employee employee){
        if (employee == null){
            throw new EmployeeNotFoundException("Employee cannot be null");
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployeeDetails(String employeeId, JsonPatch patch) {

        Optional<Employee> employeeQuery = employeeRepository.findById(employeeId);

        if (employeeQuery.isEmpty()){
            throw new EmployeeNotFoundException("Employee with id" + employeeId + "does not exist");
        }

        Employee updatedEmployee = employeeQuery.get();

        try {
            updatedEmployee = implementPatchOnEmployee(patch , updatedEmployee);
            return saveEmployee(updatedEmployee);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new EmployeeNotFoundException("Employee update Failed");
        }
    }

    @Override
    public void deleteEmployeeById(String employeeId) {

        Employee employeeToDelete = findByEmployeeId(employeeId);

        deleteEmployee(employeeToDelete);
    }

    private void deleteEmployee(Employee employeeToDelete) {

        employeeRepository.delete(employeeToDelete);
    }


    private Employee implementPatchOnEmployee(JsonPatch patch, Employee employeeToBeUpdated) throws JsonPatchException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode patched = patch.apply(objectMapper.convertValue(employeeToBeUpdated, JsonNode.class));

        return objectMapper.treeToValue(patched , Employee.class);
    }

    private String generateEmployeeId(){
        String employeeId =  UUID.randomUUID().toString();
        log.info("employee id is -> {}", employeeId);
        return employeeId;
    }

}
