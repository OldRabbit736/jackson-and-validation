package com.example.jacksonandvalidation.validation.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateDogRequest {

    String name;

    @NotNull
    double weight;

    Double foodPerDayInKilogram;

    @Override
    public String toString() {
        return "UpdateDogRequest{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", foodPerDayInKilogram=" + foodPerDayInKilogram +
                '}';
    }
}
