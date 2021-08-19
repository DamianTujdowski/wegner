package pl.wegner.documents.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wegner.documents.controller.OrderArchivalDataController;
import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.LinesPerInch;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.service.OrderArchivalDataService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderArchivalDataController.class)
class OrderArchivalDataDtoOkRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    OrderArchivalDataService service;

    private OrderArchivalDataDto orderArchivalDataDto;

    @BeforeEach
    public void setUp() {

        InkDto cyan = InkDto.builder()
                .symbol("cyan")
                .angle(Angle.BLUE)
                .build();
        InkDto magenta = InkDto.builder()
                .symbol("magenta")
                .angle(Angle.MAGENTA)
                .build();
        List<InkDto> inks = Stream.of(cyan, magenta).collect(Collectors.toList());

        orderArchivalDataDto = OrderArchivalDataDto.builder()
                .occurrence(LocalDate.of(2021,8,18))
                .fileName("Zlecenia archiwum 2021 24")
                .productionFileName("Zlecenie 02 05 2021")
                .platesDimensions("250x600")
                .platesQuantity(4)
                .lpi(LinesPerInch.MEDIUM)
                .side(PrintSide.INNER)
                .inks(inks)
                .notes("")
                .payer("Palstmoroz")
                .printHouse("Palstmoroz")
                .platesFactory("Fleksograf")
                .plateThickness(PlateThickness.THIN)
                .build();
    }

    @Test
    void shouldReturnStatusOk_WhenSavingOrderArchivalDataWithValidData() throws Exception {
        //given

        //when
        mockMvc.perform(post("/archives/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderArchivalDataDto)))
                .andExpect(status().isOk());
        //then
    }

}