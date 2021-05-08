package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.wegner.documents.model.dto.ProductionOrderDto;
import pl.wegner.documents.model.entities.Attributes;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.model.entities.ProductionOrder;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.service.ProductionOrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductionOrderController.class)
class ProductionOrderControllerOkRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductionOrderService service;

    private ProductionOrderDto productionOrderDto;
    private ProductionOrder response;

    @BeforeEach
    void setUp() {
        Attributes attributes = Attributes.builder()
                .operatorName("Karol Okrasa")
                .telephoneNumber("666222333")
                .technicalComments("thin lines present")
                .occasionalComments("")
                .build();

        OrderData orderDataButter = OrderData.builder()
                .fileName("Butter 12 03 21")
                .side(PrintSide.OUTER)
                .build();

        OrderData orderDataDoypack = OrderData.builder()
                .fileName("Doypack 12 03 21")
                .side(PrintSide.INNER)
                .build();

        List<OrderData> data = Stream.of(orderDataButter, orderDataDoypack).collect(Collectors.toList());

        productionOrderDto = ProductionOrderDto.builder()
                .designation("Zlecenie 12 03 2021")
                .occurrence(LocalDate.of(2021, 3, 12))
                .attributes(attributes)
                .orderData(data)
                .build();

        response = ProductionOrder.builder()
                .designation("Zlecenie 12 03 2021")
                .occurrence(LocalDate.of(2021, 3, 12))
                .attributes(attributes)
                .orderData(data)
                .build();
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProductionOrderWithToLongName() throws Exception {
        //given
        when(service.save(productionOrderDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/orders/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(productionOrderDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonPath = "$.designation";
        String projectJsonSourceString = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        String jsonReadResult = jsonContext.read(jsonPath);

        //then
        assertEquals("Zlecenie 12 03 2021", jsonReadResult);
    }

}