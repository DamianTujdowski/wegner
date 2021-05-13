package pl.wegner.documents.model.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.model.enums.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectDtoTest {

    private ProjectDto projectDto, projectDtoWithAlt;

    @BeforeEach
    void setUp() {
        Ink cyan = Ink.builder()
                .symbol("cyan")
                .angle(Angle.BLUE)
                .build();
        Ink magenta = Ink.builder()
                .symbol("magenta")
                .angle(Angle.MAGENTA)
                .build();
        List<Ink> inksList = Stream.of(cyan, magenta).collect(Collectors.toList());

        Alteration alt = Alteration.builder()
                .description("First PDF preparation")
                .duration(120)
                .build();
        List<Alteration> alts = Stream.of(alt).collect(Collectors.toList());

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
                .preparationBeginning(LocalDate.of(2021, 3, 26))
                .preparationEnding(LocalDate.of(2021, 4, 26))
                .build();

        projectDtoWithAlt = ProjectDto.builder()
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
    }

    @Test
    void shouldSetProjectOverallPreparationDurationToZero() {
        //given
        Project project;
        //when
        project = projectDto.map();
        //then
        assertEquals(0, project.getOverallPreparationDuration());
    }

    @Test
    void shouldSetProjectOverallPreparationDurationTo120() {
        //given
        Project project;
        //when
        project = projectDtoWithAlt.map();
        //then
        assertEquals(120, project.getOverallPreparationDuration());
    }

}