package com.example.jacksonandvalidation.validation.controller;

import com.example.jacksonandvalidation.validation.dto.CreateDogRequest;
import com.example.jacksonandvalidation.validation.dto.CreateDogResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class DogController {

    @PostMapping("/dogs/{dogId}")
    public void CreateDog(@PathVariable("dogId") Long dogId, @RequestBody @Valid CreateDogRequest createDogRequest) {
        System.out.println("good!");
    }

}
