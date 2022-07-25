package com.example.jacksonandvalidation.validation.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateDogRequest {

    @NotNull(message = "hey")
    Double weight;
}
