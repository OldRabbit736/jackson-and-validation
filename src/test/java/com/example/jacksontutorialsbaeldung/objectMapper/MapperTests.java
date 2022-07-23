package com.example.jacksontutorialsbaeldung.objectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.Data;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class MapperTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    String getFileLocation(String filename) {
        return "src/test/resources/target/" + filename;
    }

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
        Car car = objectMapper.readValue(new File(getFileLocation("car2.json")), Car.class);

        assertEquals("Black", car.getColor());
        assertEquals("BMW", car.getType());
    }

    @DisplayName("Person 테스트")
    @Test
    void personToObject() throws IOException {
        Person person = objectMapper.readValue(new File(getFileLocation("person1.json")), Person.class);

        assertEquals(36, person.getAge());
        assertEquals("JS", person.getName());
    }

    @DisplayName("만약 숫자 필드를 아예 보내지 않는다면?")
    @Test
    void personNoIntField() throws IOException {
        Person person = objectMapper.readValue(new File(getFileLocation("personWithNoAge.json")), Person.class);

        System.out.println(person);

        assertEquals("JS", person.getName());
        assertEquals(0, person.getAge());   // 0으로 초기화 시켜버린다.
    }

    @DisplayName("만약 숫자 필드를 빈 값으로 한다면?")
    @Test
    void personBlankIntField() throws IOException {
        Person person = objectMapper.readValue(new File(getFileLocation("personWithBlankAge.json")), Person.class);

        System.out.println(person);

        assertEquals("JS", person.getName());
        assertEquals(0, person.getAge());   // 0으로 초기화 시켜버린다.
    }

    @DisplayName("만약 숫자 필드에 숫자가 아닌 글자를 넣는다면?")
    @Test
    void personLetterIntField() throws IOException {
        // objectMapper.readValue(new File(getFileLocation("personWithLetterIntField.json")), Person.class);
        // --> Cannot deserialize value of type `int` from String "i don't know": not a valid `int` value

        assertThrows(
                InvalidFormatException.class,
                () -> objectMapper.readValue(new File(getFileLocation("personWithLetterIntField.json")), Person.class)
        );
    }
}

