package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wegner.documents.model.dto.InkDto;
import pl.wegner.documents.model.dto.OrderDataDto;
import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.service.OrderDataService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderDataController.class)
class OrderDataControllerOkRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderDataService service;

    private OrderDataDto orderDataDto;

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

        orderDataDto = OrderDataDto.builder()
                .fileName("Butter extra 12 03 2021")
                .platesDimensions("200x350")
                .platesQuantity(5)
                .inks(inks)
                .plateThickness(PlateThickness.THIN)
                .side(PrintSide.INNER)
                .build();
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProjectWithValidData() throws Exception {
        //given

        //when
        mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isOk());

        //then
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProjectWithValidVersionTwoFileName() throws Exception {
        //given
        orderDataDto.setFileName("Zlecenie 12 03 2021 V2");
        //when
        mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isOk());

        //then
    }

}