package com.example.jacksonandvalidation.validation.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter // 무슨 이유인지는 아직 파악은 안되지만, @Getter가 있어야 Controller에서 에러를 리턴하지 않는다.
public class CreateDogRequest {

    String name;

    @NotNull(message = "weight must be not null")
    Double weight;

    @Override
    public String toString() {
        return "CreateDogRequest{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
