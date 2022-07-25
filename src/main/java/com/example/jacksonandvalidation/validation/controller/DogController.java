package com.example.jacksonandvalidation.validation.controller;

import com.example.jacksonandvalidation.validation.dto.CreateDogRequest;
import com.example.jacksonandvalidation.validation.dto.CreateDogResponse;
import com.example.jacksonandvalidation.validation.dto.UpdateDogRequest;
import com.example.jacksonandvalidation.validation.dto.UpdateDogResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class DogController {

    @PostMapping("/dogs")
    public ResponseEntity<CreateDogResponse> CreateDog(@RequestBody @Valid CreateDogRequest createDogRequest) {
        System.out.println(createDogRequest);
        CreateDogResponse response = new CreateDogResponse("dog created");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/dogs/{dogId}")
    public ResponseEntity<UpdateDogResponse> UpdateDog(@PathVariable("dogId") Long dogId, @RequestBody @Valid UpdateDogRequest updateDogRequest) {
        UpdateDogResponse response = new UpdateDogResponse("dog updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
