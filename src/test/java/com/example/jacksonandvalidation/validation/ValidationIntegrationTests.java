package com.example.jacksonandvalidation.validation;


import com.example.jacksonandvalidation.validation.controller.DogController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



/*

알고 싶은 것
- Spring Boot 환경을 가정한다.
- 다음과 같은 controller 와 DTO 가 있다.
- json 으로 들어오는 데이터에서 해당 필드 자체가 누락되었거나 필드가 있더라도 값이 빈 칸("")인 경우를 방지하려고
  DTO 의 value1, value2 필드에 NotNull 어노테이션을 설정하였다.
- 이 때, DTO 필드에 붙은 @NotNull 은 value1, value2 모두에게 효과가 있나? 아니면 일부에게만 효과가 있나?

    Controller(@Valid @RequestBody DTO dto) {
        // some logic
    }

    DTO {
        @NotNull
        double value1;

        @NotNull
        Double value2;
    }

결론
- value1 에는 효과 없고 value2 에만 효과 있다.
- 좀 더 일반화 시키자면, primitive 타입에는 NotNull 은 효과 없다.

이유
- json string 을 jackson 에서 deserialization 할 때 만약 어떤 field 가 아예 없거나 빈 칸("") 으로 올 경우에는?
  해당 타입의 기본 값으로 세팅된다.
  - double --> 0.0
  - Double --> null
- 따라서 value1은 0.0, value2는 null 로 DTO 타입의 argument 가 만들어진다.
- 이 argument 가 @Valid 에 의해 validation 될 때,
  value1 은 0.0 이므로 validation 통과하고
  value2 는 null 이므로 validation 통과 못한다.

 */


@WebMvcTest(controllers = DogController.class)
public class ValidationIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("POST dogs 성공")
    @Test
    void postSuccess() throws Exception {

        mockMvc.perform(post("/dogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Leo\", \"weight\":1}")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());

    }

    @DisplayName("reference 숫자 타입 필드 자체를 json 에서 누락하면?")
    @Test
    void missingFieldForReferenceNumericType() throws Exception {

        /*
        Double weight 의 값이 null 값으로 deserialization 되고,
        controller argument @Valid 의 validation 과정에서 해당 필드에 걸려있는
        NotNull annotation 에 의해 NotValidException 발생
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

    @DisplayName("reference 숫자 타입 필드의 값을 json 에서 빈 값으로하면?")
    @Test
    void blankFieldForReferenceNumericType() throws Exception {

        /*
        Double weight 의 값이 null 값으로 deserialization 되고,
        controller argument @Valid 에 의한 validation 과정에서 해당 필드에 걸려있는
        NotNull annotation 에 의해 NotValidException 발생
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

    @DisplayName("PUT dogs 성공")
    @Test
    void putSuccess() throws Exception {

        mockMvc.perform(put("/dogs/123")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"Leo\", \"weight\":1, \"foodPerDayInKilogram\":0.5}")
                        // controller 내부에서 request 프린트
                        // --> UpdateDogRequest{name='Leo', weight=1.0, foodPerDayInKilogram=0.5}
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());

    }

    @DisplayName("primitive 숫자 타입 필드 자체를 json 에서 누락하면?")
    @Test
    void missingFieldForPrimitiveNumericType() throws Exception {

        /*
        double weight 의 값이 0.0 값으로 deserialization 되고,
        controller argument @Valid 에 의한 validation 과정에서 해당 필드에 걸려있는 NotNull annotation 은 아무 소용 없다.
        validation 을 통과한다.
         */

        mockMvc.perform(put("/dogs/123")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                        /*
                        json 에 weight 값이 없으나 argument 내부에서 0.0 으로 초기화된다.
                        (타입이 double 즉, primitive 이니까)
                        반면, foodPerDayInKilogram 값도 없으나 argument 내부에서 null 로 초기화된다.
                        controller 내부에서 request 프린트
                        --> UpdateDogRequest{name='null', weight=0.0, foodPerDayInKilogram=null}
                         */
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @DisplayName("primitive 숫자 타입 필드의 값을 json 에서 빈 값으로하면?")
    @Test
    void blankFieldForPrimitiveNumericType() throws Exception {

        /*
        double weight 의 값이 0.0 값으로 deserialization 되고,
        controller argument @Valid 에 의한 validation 과정에서 해당 필드에 걸려있는 NotNull annotation 은 아무 소용 없다.
        validation 을 통과한다.
         */

        mockMvc.perform(put("/dogs/123")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"Leo\", \"weight\":\"\", \"foodPerDayInKilogram\":0.5}")
                        /*
                        json 에 weight 값이 없으나 argument 내부에서 0.0 으로 초기화된다.
                        (타입이 double 즉, primitive 이니까)
                        controller 내부에서 request 프린트
                        --> UpdateDogRequest{name='Leo', weight=0.0, foodPerDayInKilogram=0.5}
                         */
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

}








