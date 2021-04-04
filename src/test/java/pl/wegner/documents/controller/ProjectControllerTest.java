package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.wegner.documents.model.dto.ProjectDto;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.service.ProjectService;

@WebMvcTest(controllers = ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProjectService service;

    private ProjectDto projectDto;

    @BeforeEach
    public void setUp() {
        projectDto = ProjectDto.builder()
//                .symbol("2103")
                .build();
    }

    @Test
    public void shouldThrowError_WhenTryingToSaveProjectWithSymbolOf3CharactersLength() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

}