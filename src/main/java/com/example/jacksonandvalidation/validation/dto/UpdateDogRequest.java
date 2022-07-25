package com.example.jacksonandvalidation.validation.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateDogRequest {

    @NotNull
    double weight;

}
