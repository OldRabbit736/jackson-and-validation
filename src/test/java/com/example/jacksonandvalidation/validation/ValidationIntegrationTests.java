package com.example.jacksonandvalidation.validation;


import com.example.jacksonandvalidation.validation.controller.DogController;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DogController.class)
public class ValidationIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void successTest() throws Exception {

        mockMvc.perform(post("/dogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Leo\", \"weight\":1}")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());

    }

    @DisplayName("reference 숫자 타입 필드 자체를 json에서 누락하면?")
    @Test
    void missingFieldForReferenceNumericType() throws Exception {

        /*
        Double weight 의 값이 null 값으로 deserialization 되고,
        controller argument @Valid 의 validation 과정에서 해당 필드에 걸려있는 NotNull annotation 에 의해 NotValidException 발생
         */

        mockMvc.perform(post("/dogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Leo\"}")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException));   // weight must be not null

    }

    @DisplayName("reference 숫자 타입 필드의 값을 json에서 빈 값으로하면?")
    @Test
    void blankFieldForReferenceNumericType() throws Exception {

        /*
        Double weight 의 값이 null 값으로 deserialization 되고,
        controller argument @Valid 의 validation 과정에서 해당 필드에 걸려있는 NotNull annotation 에 의해 NotValidException 발생
         */

        mockMvc.perform(post("/dogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Leo\", \"weight\":\"\"}")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException));   // weight must be not null

    }


}








