package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.wegner.documents.model.dto.ProjectDto;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.model.enums.Stage;
import pl.wegner.documents.service.ProjectService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProjectController.class)
public class ProjectControllerTestOkRequests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProjectService service;

    private ProjectDto projectDto;
    private Project response;

    private String jsonPathDesignation, jsonReadResult;

    @BeforeEach
    public void setUp() {
        Ink cyan = Ink.builder()
                .symbol("cyan")
                .angle(Angle.BLUE)
                .build();
        Ink magenta = Ink.builder()
                .symbol("magenta")
                .angle(Angle.MAGENTA)
                .build();
        List<Ink> inksList = Stream.of(cyan, magenta).collect(Collectors.toList());

        projectDto = ProjectDto.builder()
                .designation("Butter 200g extra")
                .symbol("21033002/01")
                .customer("Alicja dzialoszyn")
                .printHouse("Palstmoroz")
                .rollerSize(340)
                .dimensions("250x500")
                .plateThickness(PlateThickness.THIN)
                .side(PrintSide.INNER)
                .inks(inksList)
                .stage(Stage.MONTAGE)
                .alterations(new ArrayList<>())
                .overallPreparationDuration(240)
                .preparationBeginning(LocalDate.of(2021, 3, 26))
                .preparationEnding(LocalDate.of(2021, 4, 26))
                .build();

        response = Project.builder()
                .designation("Butter 200g extra")
                .symbol("21033002/01")
                .customer("Alicja dzialoszyn")
                .printHouse("Palstmoroz")
                .rollerSize(340)
                .dimensions("250x500")
                .plateThickness(PlateThickness.THIN)
                .side(PrintSide.INNER)
                .inks(inksList)
                .stage(Stage.MONTAGE)
                .alterations(new ArrayList<>())
                .overallPreparationDuration(240)
                .preparationBeginning(LocalDate.of(2021, 3, 26))
                .preparationEnding(LocalDate.of(2021, 4, 26))
                .build();
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidDesignation() throws Exception {
        //given
        jsonPathDesignation = "$.designation";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("Butter 200g extra", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidSymbol() throws Exception {
        //given
        jsonPathDesignation = "$.symbol";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("21033002/01", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidPrintHouseName() throws Exception {
        //given
        jsonPathDesignation = "$.printHouse";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("Palstmoroz", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidRollerSize() throws Exception {
        //given
        jsonPathDesignation = "$.rollerSize";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        int rollerSizeValue = jsonContext.read(jsonPathDesignation);

        //then
        assertEquals(340, rollerSizeValue);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidDimensions() throws Exception {
        //given
        jsonPathDesignation = "$.dimensions";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("250x500", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidPlateThickness() throws Exception {
        //given
        jsonPathDesignation = "$.plateThickness";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("THIN", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidSide() throws Exception {
        //given
        jsonPathDesignation = "$.side";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("INNER", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidInks() throws Exception {
        //given
        jsonPathDesignation = "$.inks[0].symbol";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        System.out.println(jsonReadResult);
        //then
        assertEquals("cyan", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidStage() throws Exception {
        //given
        jsonPathDesignation = "$.stage";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("MONTAGE", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidAlterations() throws Exception {
        //given
        jsonPathDesignation = "$.alterations";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        List alterations = jsonContext.read(jsonPathDesignation);

        //then
        assertEquals(alterations.size(), 0);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidOverallPreparationDuration() throws Exception {
        //given
        jsonPathDesignation = "$.overallPreparationDuration";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        int overallPreparationDurationValue = jsonContext.read(jsonPathDesignation);

        //then
        assertEquals(240, overallPreparationDurationValue);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidPreparationBeginning() throws Exception {
        //given
        jsonPathDesignation = "$.preparationBeginning";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("2021-03-26", jsonReadResult);
    }

    @Test
    public void shouldReturnStatusOk_WhenSavingProjectWithValidPreparationEnding() throws Exception {
        //given
        jsonPathDesignation = "$.preparationEnding";
        when(service.save(projectDto)).thenReturn(response);
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk())
                .andReturn();

        setJsonReadResult(result);

        //then
        assertEquals("2021-04-26", jsonReadResult);
    }

    private void setJsonReadResult(MvcResult result) throws UnsupportedEncodingException {
        String projectJsonSourceString = result.getResponse().getContentAsString();
        DocumentContext jsonContext = JsonPath.parse(projectJsonSourceString);
        jsonReadResult = jsonContext.read(jsonPathDesignation);
    }
}
