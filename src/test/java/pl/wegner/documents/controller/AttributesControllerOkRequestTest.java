package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.wegner.documents.model.dto.AttributesDto;
import pl.wegner.documents.model.entities.Attributes;
import pl.wegner.documents.service.AttributesService;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AttributesController.class)
class AttributesControllerOkRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AttributesService service;

    private AttributesDto attributesDto;

    @BeforeEach
    public void setUp() {
        String technicalComment = "UWAGI OGÓLNE: W PRACY WYSTĘPUJĄ LINIE, KTÓRE MAJĄ GRUBOŚĆ MNIEJSZĄ NIŻ 0,1 MM,\n" +
                "PRACA MA JUŻ ZADANY MARGINES, ZALEWKI I SKRÓT   \n" +
                "PRACA MA JUŻ ZADANĄ KRZYWĄ TONALNĄ \n" +
                "KOLORY SCHODZĄ DO 1%\n" +
                "\t      PROSIMY O ZACHOWANIE ORYGINALNEGO ROZMIARU PLIKU POST SCRIPT!";
        attributesDto = AttributesDto.builder()
                .operatorName("DT")
                .telephoneNumber("123-456-789")
                .occasionalComments("Maślanka dla Iwo")
                .technicalComments(technicalComment)
                .build();
    }

    @Test
    void shouldPassValidation_WhenSavingAttributesWithProperAllFields() throws Exception {
        //given
        attributesDto.setId(2L);
        //when
        mockMvc.perform(post("/attributes/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(attributesDto)))
                .andExpect(status().isOk());
        //then
    }

    @Test
    void shouldPassValidation_WhenSavingAttributesWithOperatorName_WithPolishSigns() throws Exception {
        //given
        attributesDto.setOperatorName("Łukasz Kuraś");
        //when
        mockMvc.perform(post("/attributes/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(attributesDto)))
                .andExpect(status().isOk());
        //then
    }

    @Test
    void shouldPassValidation_WhenSavingAttributesWithTelephoneNumberWithPrefix() throws Exception {
        //given
        attributesDto.setTelephoneNumber("+48 123-546-798");
        //when
        mockMvc.perform(post("/attributes/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(attributesDto)))
                .andExpect(status().isOk());
        //then
    }

}