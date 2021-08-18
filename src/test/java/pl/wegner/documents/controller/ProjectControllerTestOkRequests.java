package pl.wegner.documents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wegner.documents.model.dto.AlterationDto;
import pl.wegner.documents.model.dto.InkDto;
import pl.wegner.documents.model.dto.ProjectDto;
import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.model.enums.Stage;
import pl.wegner.documents.service.ProjectService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProjectController.class)
class ProjectControllerTestOkRequests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProjectService service;

    private ProjectDto projectDto;

    @BeforeEach
    void setUp() {
        InkDto cyan = InkDto.builder()
                .symbol("cyan")
                .angle(Angle.BLUE)
                .build();
        InkDto magenta = InkDto.builder()
                .symbol("magenta")
                .angle(Angle.MAGENTA)
                .build();
        List<InkDto> inksList = Stream.of(cyan, magenta).collect(Collectors.toList());

        AlterationDto alt = AlterationDto.builder()
                .description("First PDF preparation")
                .duration(120)
                .projectId(1L)
                .build();
        List<AlterationDto> alts = Stream.of(alt).collect(Collectors.toList());

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
                .alterations(alts)
                .preparationBeginning(LocalDate.of(2021, 3, 26))
                .preparationEnding(LocalDate.of(2021, 4, 26))
                .build();
        projectDto.setId(1L);
    }

    @Test
    void shouldPassValidation_WhenSavingProjectWithValidData() throws Exception {
        //given

        //when
        mockMvc.perform(post("/projects/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(projectDto)))
                .andExpect(status().isOk());

        //then
    }

}
