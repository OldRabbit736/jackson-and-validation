package com.example.jacksonandvalidation.validation;


import com.example.jacksonandvalidation.validation.controller.DogController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = DogController.class)
public class ValidationIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void test1() throws Exception {
        mockMvc.perform(post("/dogs/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"weight\":1}")
                )
                .andDo(MockMvcResultHandlers.print());

    }


}








