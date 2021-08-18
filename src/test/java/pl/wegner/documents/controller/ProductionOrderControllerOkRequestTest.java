package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wegner.documents.model.dto.AttributesDto;
import pl.wegner.documents.model.dto.InkDto;
import pl.wegner.documents.model.dto.OrderDataDto;
import pl.wegner.documents.model.dto.ProductionOrderDto;
import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.service.ProductionOrderService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @BeforeEach
    void setUp() {
        InkDto cyan = InkDto.builder()
                .symbol("cyan")
                .angle(Angle.BLUE)
                .build();
        InkDto magenta = InkDto.builder()
                .symbol("magenta")
                .angle(Angle.MAGENTA)
                .build();
        List<InkDto> inks = Stream.of(cyan, magenta).collect(Collectors.toList());

        AttributesDto attributes = AttributesDto.builder()
                .operatorName("Karol Okrasa")
                .telephoneNumber("666222333")
                .technicalComments("thin lines present")
                .occasionalComments("")
                .build();

        OrderDataDto orderDataDtoButter = OrderDataDto.builder()
                .fileName("Butter 12 03 21")
                .inks(Collections.singletonList(InkDto.builder().build()))
                .platesQuantity(4)
                .inks(inks)
                .platesDimensions("450x320")
                .plateThickness(PlateThickness.THIN)
                .side(PrintSide.OUTER)
                .build();

        OrderDataDto orderDataDtoDoypack = OrderDataDto.builder()
                .fileName("Doypack 12 03 21")
                .inks(Collections.singletonList(InkDto.builder().build()))
                .platesQuantity(4)
                .inks(inks)
                .platesDimensions("450x320")
                .plateThickness(PlateThickness.THIN)
                .side(PrintSide.INNER)
                .build();

        List<OrderDataDto> dataDtos = Stream.of(orderDataDtoButter, orderDataDtoDoypack).collect(Collectors.toList());

        productionOrderDto = ProductionOrderDto.builder()
                .designation("Zlecenie 12 03 2021")
                .occurrence(LocalDate.of(2021, 3, 12))
                .attributes(attributes)
                .orderDataDto(dataDtos)
                .build();
   }

    @Test
    void shouldReturnStatusOk_WhenSavingProductionOrderWithValidData() throws Exception {
        //given

        //when
        mockMvc.perform(post("/orders/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(productionOrderDto)))
                .andExpect(status().isOk());

        //then
    }

}