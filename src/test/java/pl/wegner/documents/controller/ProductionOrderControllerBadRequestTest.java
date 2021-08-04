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
import pl.wegner.documents.model.dto.AttributesDto;
import pl.wegner.documents.model.dto.OrderDataDto;
import pl.wegner.documents.model.dto.ProductionOrderDto;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.service.ProductionOrderService;

import java.util.List;

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
        OrderDataDto data = OrderDataDto.builder().build();
        ProductionOrderDto productionOrderDto = ProductionOrderDto.builder()
                .designation("Zlecenie 19 12 2021 2021 2021")
                .orderDataDto(List.of(data))
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

        String expectedResponse = "designation: must be formatted like -Zlecenie dd mm yyyy- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProductionOrderWithIllegalCharacter() throws Exception {
        //given
        OrderDataDto data = OrderDataDto.builder().build();
        ProductionOrderDto productionOrderDto = ProductionOrderDto.builder()
                .designation("Zlecenie 19 12 2021 @2")
                .orderDataDto(List.of(data))
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

        String expectedResponse = "designation: must be formatted like -Zlecenie dd mm yyyy- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProductionOrderWithOnlyCapitalLetters() throws Exception {
        //given
        OrderDataDto data = OrderDataDto.builder().build();
        ProductionOrderDto productionOrderDto = ProductionOrderDto.builder()
                .designation("ZLECENIE 19 12 2021")
                .orderDataDto(List.of(data))
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

        String expectedResponse = "designation: must be formatted like -Zlecenie dd mm yyyy- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProductionOrderWithDoubleVersionMarkFileName() throws Exception {
        //given
        OrderDataDto data = OrderDataDto.builder().build();
        ProductionOrderDto productionOrderDto = ProductionOrderDto.builder()
                .designation("Zlecenie 19 12 2021 V2 V1")
                .orderDataDto(List.of(data))
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

        String expectedResponse = "designation: must be formatted like -Zlecenie dd mm yyyy- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProductionOrderWithInvalidAttributes() throws Exception {
        //given
        OrderDataDto data = OrderDataDto.builder().build();
        AttributesDto attributes = AttributesDto.builder()
                .operatorName("DT")
                .occasionalComments("Zalewki nie rozlewać")
                .technicalComments("Zamowienie na juz")
                .build();
        ProductionOrderDto productionOrderDto = ProductionOrderDto.builder()
                .designation("Zlecenie 19 12 2021")
                .orderDataDto(List.of(data))
                .attributes(attributes)
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

        String expectedResponse = "attributes.telephoneNumber: must be provided";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

}