package com.example.jacksonandvalidation.validation.dto;

import lombok.Getter;

@Getter
public class UpdateDogResponse {

    String message;

    public UpdateDogResponse(String message) {
        this.message = message;
    }
}
