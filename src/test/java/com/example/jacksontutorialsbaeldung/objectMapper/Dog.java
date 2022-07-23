package com.example.jacksontutorialsbaeldung.objectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Dog {

    public String name;
    public Integer age;

    public Dog(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
