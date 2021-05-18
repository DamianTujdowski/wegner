package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.wegner.documents.model.dto.AlterationDto;
import pl.wegner.documents.model.dto.ProjectDto;
import pl.wegner.documents.service.AlterationService;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlterationController.class)
class AlterationControllerBadRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    AlterationService service;

    private String jsonPathDesignation, jsonReadResult;

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingAlterationWithNullDescription() throws Exception {
        //given
        AlterationDto alterationDto = AlterationDto.builder()
                .build();
        jsonPathDesignation = "$.message";

        //when
        MvcResult result = mockMvc.perform(post("/alterations/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(alterationDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "description: must be provided";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingAlterationWithOnlySpaceDescription() throws Exception {
        //given
        AlterationDto alterationDto = AlterationDto.builder()
                .description("     ")
                .build();
        jsonPathDesignation = "$.message";

        //when
        MvcResult result = mockMvc.perform(post("/alterations/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(alterationDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "description: must be provided";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingAlterationWithIllegalCharactersDescription() throws Exception {
        //given
        AlterationDto alterationDto = AlterationDto.builder()
                .description("abcde% @")
                .build();
        jsonPathDesignation = "$.message";

        //when
        MvcResult result = mockMvc.perform(post("/alterations/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(alterationDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "description: can contain only Polish letters, numbers, space and new line";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }


    private void setJsonReadResult(MvcResult result) throws UnsupportedEncodingException {
        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        jsonReadResult = jsonContext.read(jsonPathDesignation);
    }

}