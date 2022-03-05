package com.sowloo.sowloowrita.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;



@Data
public class ApiResponse {

    @JsonIgnore
    private boolean isSuccessful;
    @JsonIgnore
    private String message;

    public ApiResponse(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }
}
