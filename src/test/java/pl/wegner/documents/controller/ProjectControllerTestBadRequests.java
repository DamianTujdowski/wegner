package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.wegner.documents.model.dto.ProjectDto;
import pl.wegner.documents.service.ProjectService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProjectController.class)
class ProjectControllerTestBadRequests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProjectService service;

//    private ProjectDto projectDto;
//
//    @BeforeEach
//    public void setUp() {
//        projectDto = ProjectDto.builder()
//                .symbol("2103")
//                .build();
//    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithNullDesignation() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "designation: must be provided";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithOnlySpacesDesignation() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .designation("     ")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "designation: must be provided";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithFiveLettersLongDesignation() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .designation("abcde")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "designation: length must be between 10 and 50 characters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithSixtyLettersLongDesignation() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .designation("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "designation: length must be between 10 and 50 characters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithDesignationWithIllegalCharacters() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .designation("abcde% @")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "designation: length must be between 10 and 50 characters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithNullSymbol() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "symbol: must be provided";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithThreeDigitsSymbol() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .symbol("210")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "symbol: can contain only digits and slash. " +
                "There must be 8 digits before slash and 2 digits after it";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithSymbolWithWronglyPlacedSlash() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .symbol("2103310/101")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "symbol: can contain only digits and slash. " +
                "There must be 8 digits before slash and 2 digits after it";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithSymbolWithThreeDigitsAfterSlash() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .symbol("21033101/011")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "symbol: can contain only digits and slash. " +
                "There must be 8 digits before slash and 2 digits after it";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithSymbolWithSixDigitsBeforeSlash() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .symbol("210330/01")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "symbol: can contain only digits and slash. " +
                "There must be 8 digits before slash and 2 digits after it";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithSymbolWithNoSlash() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .symbol("2103300201")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "symbol: can contain only digits and slash. " +
                "There must be 8 digits before slash and 2 digits after it";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithNullCustomer() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "customer: must be provided";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithOnlySpacesCustomer() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .customer("     ")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "customer: must be provided";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithTwoLettersLongCustomer() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .customer("ab")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "customer: name length must be between 3 and 20 characters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithSixtyLettersLongCustomer() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .customer("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "customer: name length must be between 3 and 20 characters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithCustomerWithIllegalCharacters() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .customer("abcde% @")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "customer: name can contain only letters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithNullPrintHouse() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "printHouse: must be provided";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithOnlySpacesPrintHouse() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .printHouse("     ")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "printHouse: must be provided";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithTwoLettersLongPrintHouse() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .printHouse("ab")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "printHouse: name length must be between 3 and 20 characters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithSixtyLettersLongPrintHouse() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .printHouse("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "printHouse: name length must be between 3 and 20 characters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithPrintHouseWithIllegalCharacters() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .printHouse("abcde% @")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "printHouse: name can contain only letters";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithNullRollerSize() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "rollerSize: must be provided";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWith200RollerSize() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .rollerSize(200)
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "rollerSize: must be greater than or equal to 250";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWith650RollerSize() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .rollerSize(650)
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "rollerSize: must be less than or equal to 600";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithNullDimensions() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "dimensions: must be provided";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithDimensionsWithNoX() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .dimensions("200300")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "Either width and height must contain 3 or 4 digits and must be separated with 'x'";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    public void shouldThrowMethodArgumentNotValidException_WhenTryingToSaveProjectWithToBigWidth() throws Exception {
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .dimensions("200x30000")
                .build();
        //when
        MvcResult result = mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = result
                .getResponse()
                .getContentAsString();

        String expectedResponse = "Either width and height must contain 3 or 4 digits and must be separated with 'x'";
        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }
}