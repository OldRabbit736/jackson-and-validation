package com.example.jacksonandvalidation.validation.dto;

import lombok.Getter;

// Getter 가 없으면 controller 에서 response 를 반환하는 과정에서 에러 발생.
// 아마 Jackson 에서 deserialization 에 문제가 생기는 것 같다.: HttpMediaTypeNotAcceptableException
@Getter
public class CreateDogResponse {
    String message;

    public CreateDogResponse(String message) {
        this.message = message;
    }
}
