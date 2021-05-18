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
import pl.wegner.documents.model.dto.AlterationDto;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.service.AlterationService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlterationController.class)
class AlterationControllerOkRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    AlterationService service;

    private AlterationDto alterationDto;
    private Alteration response;

    private String jsonPathDesignation, jsonReadResult;

    @BeforeEach
    void setUp() {
        alterationDto = AlterationDto.builder()
                .occurrence(LocalDate.of(2021, 3, 10))
                .duration(35)
                .build();
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProjectWithValidDesignation() throws Exception {
        //given
        jsonPathDesignation = "$.description";
        alterationDto.setDescription("Dodanie dodatkowego logo i zmaksymalizowanie obrazka");
        response = alterationDto.map();
        when(service.save(alterationDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/alterations/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(alterationDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("Dodanie dodatkowego logo i zmaksymalizowanie obrazka", jsonReadResult);
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProjectWithDesignationWithLineFeedCharacters() throws Exception {
        //given
        jsonPathDesignation = "$.description";
        alterationDto.setDescription("Dodanie dodatkowego logo\n i zmaksymalizowanie obrazka");
        response = alterationDto.map();
        when(service.save(alterationDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/alterations/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(alterationDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("Dodanie dodatkowego logo\n i zmaksymalizowanie obrazka", jsonReadResult);
    }


    @Test
    void shouldReturnStatusOk_WhenSavingProjectWithDesignationWithTabCharacters() throws Exception {
        //given
        jsonPathDesignation = "$.description";
        alterationDto.setDescription("Dodanie dodatkowego logo\t i zmaksymalizowanie obrazka");
        response = alterationDto.map();
        when(service.save(alterationDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/alterations/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(alterationDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("Dodanie dodatkowego logo\t i zmaksymalizowanie obrazka", jsonReadResult);
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProjectWithDesignationWithLineFeedAndTabCharacters() throws Exception {
        //given
        jsonPathDesignation = "$.description";
        alterationDto.setDescription("Dodanie\t dodatkowego logo\n i zmaksymalizowanie obrazka");
        response = alterationDto.map();
        when(service.save(alterationDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/alterations/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(alterationDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("Dodanie\t dodatkowego logo\n i zmaksymalizowanie obrazka", jsonReadResult);
    }


    private void setJsonReadResult(MvcResult result) throws UnsupportedEncodingException {
        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        jsonReadResult = jsonContext.read(jsonPathDesignation);
    }

}