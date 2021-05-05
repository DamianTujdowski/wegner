package pl.wegner.documents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.wegner.documents.model.dto.ProjectDto;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.repository.ProjectRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    private ProjectService service;

    @Mock
    private ProjectRepository repository;

    private Project projectWithNotInitializedAlterationsList, projectWithEmptyAlterationsList,projectWithOneAlteration,
            projectWithTwoAlterations;

    private ProjectDto dtoWithTwoAlterations, dtoWithThreeAlterations;

    @BeforeEach
    void setUp() {
        Alteration textAlt = Alteration.builder()
                .description("Text alteration")
                .occurrence(LocalDate.of(2021, 2, 13))
                .duration(40)
                .build();

        Alteration colorAlt = Alteration.builder()
                .description("Color alteration")
                .occurrence(LocalDate.of(2021, 2, 17))
                .duration(70)
                .build();

        Alteration sizeAlt = Alteration.builder()
                .description("Size alteration")
                .occurrence(LocalDate.of(2021, 2, 18))
                .duration(10)
                .build();

        List<Alteration> oneAlter = Stream.of(sizeAlt).collect(Collectors.toList());
        List<Alteration> twoAlters = Stream.of(textAlt, colorAlt).collect(Collectors.toList());
        List<Alteration> threeAlter = Stream.of(textAlt, colorAlt, sizeAlt).collect(Collectors.toList());

        projectWithNotInitializedAlterationsList = Project.builder()
                .id(1)
                .designation("Alicja")
                .customer("Komsomolec")
                .build();

        projectWithEmptyAlterationsList = Project.builder()
                .id(1)
                .designation("Alicja")
                .customer("Komsomolec")
                .alterations(new ArrayList<>())
                .build();

        projectWithOneAlteration = Project.builder()
                .id(1)
                .designation("Alicja")
                .customer("Komsomolec")
                .alterations(oneAlter)
                .build();

        projectWithTwoAlterations = Project.builder()
                .id(1)
                .designation("Alicja")
                .customer("Komsomolec")
                .alterations(twoAlters)
                .build();

        dtoWithTwoAlterations = ProjectDto.builder()
                .id(1)
                .designation("Alicja")
                .customer("Komsomolec")
                .alterations(twoAlters)
                .build();

        dtoWithThreeAlterations = ProjectDto.builder()
                .id(1)
                .designation("Alicja")
                .customer("Komsomolec")
                .alterations(threeAlter)
                .build();
    }

    @Test
    void shouldCountOverallPreparationTimeEqualTo110_whenEditedProjectHasNotInitializedAlterationsList() {
        //given
        Project edited;
        //when
        when(repository.findById(1L)).thenReturn(Optional.of(projectWithNotInitializedAlterationsList));
        edited = service.edit(dtoWithTwoAlterations);
        //then
        assertEquals(110, edited.getOverallPreparationDuration());
    }

    @Test
    void shouldCountOverallPreparationTimeEqualTo110_whenEditedProjectHasEmptyAlterationsList() {
        //given
        Project edited;
        //when
        when(repository.findById(1L)).thenReturn(Optional.of(projectWithEmptyAlterationsList));
        edited = service.edit(dtoWithTwoAlterations);
        //then
        assertEquals(110, edited.getOverallPreparationDuration());
    }

    @Test
    void shouldCountOverallPreparationTimeEqualTo110_whenEditedProjectHasNoCommonAlterations() {
        //given
        Project edited;
        //when
        when(repository.findById(1L)).thenReturn(Optional.of(projectWithOneAlteration));
        edited = service.edit(dtoWithTwoAlterations);
        //then
        assertEquals(110, edited.getOverallPreparationDuration());
    }

    @Test
    void shouldCountOverallPreparationTimeEqualTo120_whenEditedProjectHasCommonAlterations() {
        //given
        Project edited;
        //when
        when(repository.findById(1L)).thenReturn(Optional.of(projectWithTwoAlterations));
        edited = service.edit(dtoWithThreeAlterations);
        //then
        assertEquals(120, edited.getOverallPreparationDuration());
    }

}