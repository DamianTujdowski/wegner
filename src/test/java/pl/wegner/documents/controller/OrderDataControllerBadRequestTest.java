package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.wegner.documents.model.dto.OrderDataDto;
import pl.wegner.documents.service.OrderDataService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderDataController.class)
class OrderDataControllerBadRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderDataService dataService;

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingOrderDataWithNullFileName() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: must be provided";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingOrderDataWithToLongFileName() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .fileName("Butter extra 300g lactose free 23 03 2021")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: length must be between 10 and 30 characters";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingOrderDataWithFileNameContainingIllegalCharacters() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .fileName("BUTTER 23 03 2021 !")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: can contain only letters, digits and underscore";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingOrderDataWithDateAtBeginningFileName() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .fileName("23 03 2021 butter")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: format must be like -Zlecenie yyyy mm dd- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingOrderDataWithDoubleVersionMarkFileName() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .fileName("Zlecenie 23 03 2021 VV2")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: format must be like -Zlecenie yyyy mm dd- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }


}