package com.example.jacksonandvalidation.jacksonobjectmapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Car {

    private String color;
    private String type;

    public Car(String color, String type) {
        this.color = color;
        this.type = type;
    }
}
