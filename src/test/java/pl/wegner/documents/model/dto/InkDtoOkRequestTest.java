package pl.wegner.documents.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wegner.documents.controller.InkController;
import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.LinesPerInch;
import pl.wegner.documents.service.InkService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InkController.class)
class InkDtoOkRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    InkService service;

    private InkDto inkDto;

    @BeforeEach
    public void setUp() {
        inkDto = InkDto.builder()
                .symbol("Pantone 1725 C")
                .angle(Angle.NONE)
                .lpi(LinesPerInch.APLA)
                .projectId(14L)
                .orderDataId(4L)
                .orderArchivalDataId(6L)
                .build();
    }

    @Test
    void shouldReturnStatusOk_WhenSavingAttributesWithValidData() throws Exception {
        //given

        //when
        mockMvc.perform(post("/inks/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(inkDto)))
                .andExpect(status().isOk());
        //then
    }
}