package com.sowloo.sowloowrita.data.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEmployeeResponseDto {

    private String id;


    private String userName;

    private String generatedEmployeeId;

}
