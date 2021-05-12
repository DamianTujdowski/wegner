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
import pl.wegner.documents.model.dto.ProjectDto;
import pl.wegner.documents.model.dto.ProofDto;
import pl.wegner.documents.service.ProofService;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProofController.class)
class ProofControllerBadRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProofService service;

    private String jsonPathDesignation, jsonReadResult;

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProofWithNullDesignation() throws Exception {
        //given
        ProofDto proofDto = ProofDto.builder()
                .build();
        jsonPathDesignation = "$.message";
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "designation: must be provided";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProofWithOnlySpacesDesignation() throws Exception {
        //given
        ProofDto proofDto = ProofDto.builder()
                .designation("    ")
                .build();
        jsonPathDesignation = "$.message";
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "designation: must be provided";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProofWithFiveLettersLongDesignation() throws Exception {
        //given
        ProofDto proofDto = ProofDto.builder()
                .designation("abcde")
                .build();
        jsonPathDesignation = "$.message";
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "designation: length must be between 10 and 50 characters";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProofWithSixtyLettersLongDesignation() throws Exception {
        //given
        ProofDto proofDto = ProofDto.builder()
                .designation("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij")
                .build();
        jsonPathDesignation = "$.message";
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "designation: length must be between 10 and 50 characters";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProofWithDesignationWithIllegalCharacters() throws Exception {
        //given
        ProofDto proofDto = ProofDto.builder()
                .designation("abcde% @")
                .build();
        jsonPathDesignation = "$.message";
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "designation: length must be between 10 and 50 characters";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProofWithZeroQuantity() throws Exception {
        //given
        ProofDto proofDto = ProofDto.builder()
                .quantity(0)
                .build();
        jsonPathDesignation = "$.message";
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "minimal quantity is 1";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingProofWith20Quantity() throws Exception {
        //given
        ProofDto proofDto = ProofDto.builder()
                .quantity(20)
                .build();
        jsonPathDesignation = "$.message";
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        setJsonReadResult(result);
        String expectedResponse = "maximal quantity is 10";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    private void setJsonReadResult(MvcResult result) throws UnsupportedEncodingException {
        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        jsonReadResult = jsonContext.read(jsonPathDesignation);
    }

}