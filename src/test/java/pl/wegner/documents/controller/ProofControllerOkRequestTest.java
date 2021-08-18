package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wegner.documents.model.dto.ProofDto;
import pl.wegner.documents.model.enums.SendMethod;
import pl.wegner.documents.service.ProofService;

import java.time.LocalDate;

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

    @BeforeEach
    void setUp() {

        proofDto = ProofDto.builder()
                .printDate(LocalDate.of(2021, 3, 12))
                .designation("Maslo extra 200g 12 03 2021 Proof")
                .quantity(2)
                .customer("Euro-Eko-Pak")
                .dimension("250x250")
                .sendMethod(SendMethod.SENT_INDIVIDUALLY)
                .payer("Euro-Eko-Pak")
                .build();
        proofDto.setId(1L);
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProofWithValidData() throws Exception {
        //given

        //when
        mockMvc.perform(post("/proofs/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(proofDto)))
                .andExpect(status().isOk());
        //then
    }

}