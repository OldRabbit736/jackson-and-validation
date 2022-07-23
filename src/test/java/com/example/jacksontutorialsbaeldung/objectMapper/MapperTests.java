package com.example.jacksontutorialsbaeldung.objectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class MapperTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    void objectToJson() throws IOException {
        Car car = new Car("yellow", "renault");
        objectMapper.writeValue(new File("./src/test/resources/target/car1.json"), car);
    }

    @Test
    @Order(2)
    void jsonToObject() throws IOException {
        // 방법 1 : String 으로부터 값을 읽는다.
        //String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        //Car car = objectMapper.readValue(json, Car.class);

        // 방법 2 : 파일로부터 값을 읽는다.
        Car car = objectMapper.readValue(new File("src/test/resources/target/car2.json"), Car.class);

        assertEquals("Black", car.getColor());
        assertEquals("BMW", car.getType());
    }
}

