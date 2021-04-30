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
import pl.wegner.documents.model.dto.OrderDataDto;
import pl.wegner.documents.service.OrderDataService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderDataController.class)
class OrderDataControllerBadRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderDataService dataService;

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenSavingProjectWithNullFileName() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: must be provided";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenSavingProjectWithToLongFileName() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .fileName("Zlecenie zlecenie 23 03 2021")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: length must be between 19 and 23 characters";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenSavingProjectWithCapitalLettersFileName() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .fileName("ZLECENIE 23 03 2021")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: format must be like -Zlecenie yyyy mm dd- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenSavingProjectWithDateAtBeginningFileName() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .fileName("23 03 2021 zlecenie")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: format must be like -Zlecenie yyyy mm dd- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenSavingProjectWithDoubleVersionMarkFileName() throws Exception {
        //given
        OrderDataDto orderDataDto = OrderDataDto.builder()
                .fileName("Zlecenie 23 03 2021 VV2")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/data/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(orderDataDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonPathError = "$.message";
        String actualResponse = result
                .getResponse()
                .getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(actualResponse);
        String jsonReadResult = jsonContext.read(jsonPathError);

        String expectedResponse = "fileName: format must be like -Zlecenie yyyy mm dd- with optional version mark";
        //then
        assertTrue(jsonReadResult.contains(expectedResponse));
    }


}