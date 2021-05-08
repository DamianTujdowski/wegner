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
import pl.wegner.documents.model.dto.ProductionOrderDto;
import pl.wegner.documents.service.ProductionOrderService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductionOrderController.class)
class ProductionOrderControllerBadRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductionOrderService service;

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProductionOrderWithToLongName() throws Exception {
        //given
        ProductionOrderDto productionOrderDto = ProductionOrderDto.builder()
                .designation("Zlecenie 19 12 2021 2021 2021")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/orders/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(productionOrderDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "designation: must be formatted like -Zlecenie yyyy mm dd- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProductionOrderWithIllegalCharacter() throws Exception {
        //given
        ProductionOrderDto productionOrderDto = ProductionOrderDto.builder()
                .designation("Zlecenie 19 12 2021 @2")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/orders/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(productionOrderDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "designation: must be formatted like -Zlecenie yyyy mm dd- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProductionOrderWithOnlyCapitalLetters() throws Exception {
        //given
        ProductionOrderDto productionOrderDto = ProductionOrderDto.builder()
                .designation("ZLECENIE 19 12 2021")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/orders/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(productionOrderDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "designation: must be formatted like -Zlecenie yyyy mm dd- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProductionOrderWithDoubleVersionMarkFileName() throws Exception {
        //given
        ProductionOrderDto productionOrderDto = ProductionOrderDto.builder()
                .designation("Zlecenie 19 12 2021 V2 V1")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/orders/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(productionOrderDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "designation: must be formatted like -Zlecenie yyyy mm dd- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

}