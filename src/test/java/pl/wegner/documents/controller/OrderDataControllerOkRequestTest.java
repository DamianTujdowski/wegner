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
import pl.wegner.documents.model.dto.OrderDataDto;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.service.OrderDataService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
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
    private OrderData response;

    private String jsonPathDesignation, jsonReadResult;

    @BeforeEach
    public void setUp() {
        Ink cyan = Ink.builder()
                .symbol("cyan")
                .angle(Angle.BLUE)
                .build();
        Ink magenta = Ink.builder()
                .symbol("magenta")
                .angle(Angle.MAGENTA)
                .build();
        List<Ink> inksList = Stream.of(cyan, magenta).collect(Collectors.toList());

        orderDataDto = OrderDataDto.builder()
                .fileName("Zlecenie 12 03 2021")
                .platesDimensions("200x350")
                .platesQuantity(5)
                .inks(inksList)
                .plateThickness(PlateThickness.THIN)
                .side(PrintSide.INNER)
                .build();

        response = OrderData.builder()
                .fileName("Zlecenie 12 03 2021")
                .platesDimensions("200x350")
                .platesQuantity(5)
                .inks(inksList)
                .plateThickness(PlateThickness.THIN)
                .side(PrintSide.INNER)
                .build();
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidFileName() throws Exception {
        //given
        jsonPathDesignation = "$.fileName";
        when(service.save(orderDataDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("Zlecenie 12 03 2021", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidVersionFileName() throws Exception {
        //given
        orderDataDto.setFileName("Zlecenie 12 03 2021 V2");
        response.setFileName("Zlecenie 12 03 2021 V2");
        jsonPathDesignation = "$.fileName";
        when(service.save(orderDataDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("Zlecenie 12 03 2021 V2", jsonReadResult);
    }

    private void setJsonReadResult(MvcResult result) throws UnsupportedEncodingException {
        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        jsonReadResult = jsonContext.read(jsonPathDesignation);
    }


}