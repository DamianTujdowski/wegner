package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wegner.documents.model.dto.AlterationDto;
import pl.wegner.documents.service.AlterationService;

import java.time.LocalDate;

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

    @BeforeEach
    void setUp() {
        alterationDto = AlterationDto.builder()
                .occurrence(LocalDate.of(2021, 3, 10))
                .description("Zmiana kodu kreskowego")
                .duration(35)
                .build();
    }

    @Test
    void shouldReturnStatusOk_WhenSavingProjectWithValidData() throws Exception {
        //given

        //when
        mockMvc.perform(post("/alterations/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(alterationDto)))
                .andExpect(status().isOk());

        //then
    }

}