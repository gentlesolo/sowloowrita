package com.sowloo.sowloowrita.data.dtos.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeInfo {

    private String firstName;

    private String lastName;

    private String emailId;
}
