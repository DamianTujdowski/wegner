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
import pl.wegner.documents.model.dto.ProofDto;
import pl.wegner.documents.model.entities.Proof;
import pl.wegner.documents.model.enums.SendMethod;
import pl.wegner.documents.service.ProofService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProofController.class)
class ProofControllerOkRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProofService service;

    private ProofDto proofDto;
    private Proof response;

    private String jsonPathDesignation, jsonReadResult;

    @BeforeEach
    void setUp() {

        proofDto = ProofDto.builder()
                .id(1)
                .printDate(LocalDate.of(2021, 3, 12))
                .designation("Maslo extra 200g 12 03 2021 Proof")
                .quantity(2)
                .customer("Euro-Eko-Pak")
                .dimension("250x250")
                .sendMethod(SendMethod.SENT_INDIVIDUALLY)
                .payer("Euro-Eko-Pak")
                .build();

        response = Proof.builder()
                .id(1)
                .printDate(LocalDate.of(2021, 3, 12))
                .designation("Maslo extra 200g 12 03 2021 Proof")
                .quantity(2)
                .customer("Euro-Eko-Pak")
                .dimension("250x250")
                .sendMethod(SendMethod.SENT_INDIVIDUALLY)
                .payer("Euro-Eko-Pak")
                .build();
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProofWithValidPrintDate() throws Exception {
        //given
        jsonPathDesignation = "$.printDate";
        when(service.save(proofDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);
        //then
        assertEquals("2021-03-12", jsonReadResult);
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProofWithValidDesignation() throws Exception {
        //given
        jsonPathDesignation = "$.designation";
        when(service.save(proofDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);
        //then
        assertEquals("Maslo extra 200g 12 03 2021 Proof", jsonReadResult);
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProofWithValidQuantity() throws Exception {
        //given
        jsonPathDesignation = "$.quantity";
        when(service.save(proofDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isOk())
                .andReturn();

        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        int quantity = jsonContext.read(jsonPathDesignation);
        //then
        assertEquals(2, quantity);
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProofWithValidCustomer() throws Exception {
        //given
        jsonPathDesignation = "$.customer";
        when(service.save(proofDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);
        //then
        assertEquals("Euro-Eko-Pak", jsonReadResult);
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProofWithValidDimension() throws Exception {
        //given
        jsonPathDesignation = "$.dimension";
        when(service.save(proofDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);
        //then
        assertEquals("250x250", jsonReadResult);
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProofWithValidSendMethod() throws Exception {
        //given
        jsonPathDesignation = "$.sendMethod";
        when(service.save(proofDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);
        //then
        assertEquals("SENT_INDIVIDUALLY", jsonReadResult);
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProofWithValidPayer() throws Exception {
        //given
        jsonPathDesignation = "$.payer";
        when(service.save(proofDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);
        //then
        assertEquals("Euro-Eko-Pak", jsonReadResult);
    }


    private void setJsonReadResult(MvcResult result) throws UnsupportedEncodingException {
        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        jsonReadResult = jsonContext.read(jsonPathDesignation);
    }

}