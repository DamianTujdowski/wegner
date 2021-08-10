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
import pl.wegner.documents.model.dto.AttributesDto;
import pl.wegner.documents.service.AttributesService;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AttributesController.class)
class AttributesControllerBadRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AttributesService service;

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingAttributesWithInvalidOperatorName() throws Exception {
        //given
        AttributesDto attributesDto = AttributesDto.builder()
                .operatorName("Roman?Kostrzewa")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/attributes/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(attributesDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonReadResult = setJsonReadResult(result);
        String expectedResponse = "operatorName: can contain only letters (including Polish) and space";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingAttributesWithInvalidTelephoneNumber() throws Exception {
        //given
        AttributesDto attributesDto = AttributesDto.builder()
                .telephoneNumber("123-456x789")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/attributes/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(attributesDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonReadResult = setJsonReadResult(result);
        String expectedResponse = "telephoneNumber: preferable format NNN-NNN-NNN, dashes can be replaced with spaces, \"+48 \" prefix allowed";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingAttributesWithInvalidOccasionalComments() throws Exception {
        //given
        AttributesDto attributesDto = AttributesDto.builder()
                .occasionalComments("alalala bebebeb wwa lelefala ^")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/attributes/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(attributesDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonReadResult = setJsonReadResult(result);
        String expectedResponse = "occasionalComments: can contain only letters (including Polish), numbers, whitespace characters and - _ . ,";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_WhenSavingAttributesWithInvalidTechnicalComments() throws Exception {
        //given
        AttributesDto attributesDto = AttributesDto.builder()
                .technicalComments("alalala %bebebeb wwa lelefala")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/attributes/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(attributesDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonReadResult = setJsonReadResult(result);
        String expectedResponse = "technicalComments: can contain only letters (including Polish), numbers, whitespace characters and - _ . ,";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    private String setJsonReadResult(MvcResult result) throws UnsupportedEncodingException {
        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        return jsonContext.read("$.message");
    }

}