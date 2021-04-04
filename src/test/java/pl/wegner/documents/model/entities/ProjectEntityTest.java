package pl.wegner.documents.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProjectEntityTest {

    private Project projectWithNoInksAndAlterations, projectWithYellowMagentaInks, projectWithTextAndColorAlterations;

    private Ink yellow, magenta, cyan, magentaModified;

    private Alteration colorAlter, textAlter, colorAlterModified;

    @BeforeEach
    public void init() {
        yellow = Ink.builder()
                .id(1)
                .symbol("Yellow")
                .projectId(1)
                .build();

        magenta = Ink.builder()
                .id(2)
                .symbol("Magenta")
                .projectId(1)
                .build();

        cyan = Ink.builder()
                .id(3)
                .symbol("Cyan")
                .projectId(1)
                .build();

        List<Ink> inks = Stream.of(yellow, magenta).collect(Collectors.toList());

        magentaModified = Ink.builder()
                .id(2)
                .symbol("Modified magenta")
                .projectId(1)
                .build();

        colorAlter = Alteration.builder()
                .occurrence(LocalDate.of(2020, 1, 12))
                .description("Red changed to blue")
                .duration(50)
                .projectId(1)
                .build();

        textAlter = Alteration.builder()
                .occurrence(LocalDate.of(2020, 1, 16))
                .description("Text changed to capitals")
                .duration(110)
                .projectId(1)
                .build();

        List<Alteration> alterations = Stream.of(colorAlter, textAlter).collect(Collectors.toList());

        colorAlterModified = Alteration.builder()
                .occurrence(LocalDate.of(2020, 1, 12))
                .description("Red changed to magenta")
                .duration(50)
                .projectId(1)
                .build();

        projectWithNoInksAndAlterations = Project.builder()
                .id(1)
                .designation("Alicja")
                .customer("Komsomolec")
                .inks(new ArrayList<>())
                .alterations(new ArrayList<>())
                .build();

        projectWithYellowMagentaInks = Project.builder()
                .id(1)
                .designation("Alicja")
                .customer("Komsomolec")
                .inks(inks)
                .build();

        projectWithTextAndColorAlterations = Project.builder()
                .id(1)
                .designation("Alicja")
                .customer("Komsomolec")
                .alterations(alterations)
                .build();
    }

    @Test
    public void whenProjectHasNoInks_shouldAddAllInks() {
        //given
        List<Ink> inks = Stream.of(yellow, magenta).collect(Collectors.toList());
        //when
        projectWithNoInksAndAlterations.setInks(inks);
        //then
        assertEquals(2, projectWithNoInksAndAlterations.getInks().size());
    }

    @Test
    public void whenProjectInksListIsSetWithInksListWithNoCommonElements_shouldReplaceEntityInksListWithNewInks() {
        //given
        List<Ink> inks = Stream.of(cyan).collect(Collectors.toList());
        //when
        projectWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(1, projectWithYellowMagentaInks.getInks().size());
    }

    @Test
    public void whenProjectInksListIsSetWithIdenticalInksList_shouldNotMakeAnyChangesToEntityInksList() {
        //given
        List<Ink> inks = Stream.of(yellow, magenta).collect(Collectors.toList());

        //when
        projectWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(2, projectWithYellowMagentaInks.getInks().size());
        assertEquals("Yellow", projectWithYellowMagentaInks.getInks().get(0).getSymbol());
    }

    @Test
    public void whenProjectInksListIsSetWithInksListWithChangedElements_shouldMakeChangesToEntityInksList() {
        //given
        List<Ink> inks = Stream.of(yellow, magentaModified).collect(Collectors.toList());
        //when
        projectWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(2, projectWithYellowMagentaInks.getInks().size());
        assertEquals("Modified magenta", projectWithYellowMagentaInks.getInks().get(1).getSymbol());
    }

    @Test
    public void whenProjectInksListIsSetWithInksListWithRemovedElements_shouldRemoveMissingElementFromEntityInksList() {
        //given
        List<Ink> inks = Stream.of(yellow).collect(Collectors.toList());
        //when
        projectWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(1, projectWithYellowMagentaInks.getInks().size());
        assertEquals("Yellow", projectWithYellowMagentaInks.getInks().get(0).getSymbol());
    }

    @Test
    public void whenProjectInksListIsSetWithInksListWithNewElements_shouldAddAllNewElements() {
        //given
        List<Ink> inks = Stream.of(cyan, yellow, magenta).collect(Collectors.toList());
        //when
        projectWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(3, projectWithYellowMagentaInks.getInks().size());
    }

    @Test
    public void whenProjectInksListIsSetWithEmptyInksList_shouldRemoveAllElementFromEntityInksList() {
        //given
        List<Ink> inks = new ArrayList<>();
        //when
        projectWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(0, projectWithYellowMagentaInks.getInks().size());
    }

    @Test
    public void whenProjectHasNoAlterations_shouldAddAllAlterations() {
        //given
        List<Alteration> alterations = Stream.of(colorAlter, textAlter).collect(Collectors.toList());
        //when
        projectWithNoInksAndAlterations.setAlterations(alterations);
        //then
        assertEquals(2, projectWithNoInksAndAlterations.getAlterations().size());
    }

    @Test
    public void whenProjectAlterationsListIsSetWithIdenticalAlterationsList_shouldNotMakeAnyChangesToEntityAlterationsList() {
        //given
        List<Alteration> alterations = Stream.of(colorAlter, textAlter).collect(Collectors.toList());
        //when
        projectWithTextAndColorAlterations.setAlterations(alterations);
        //then
        assertEquals(2, projectWithTextAndColorAlterations.getAlterations().size());
        assertEquals("Red changed to blue", projectWithTextAndColorAlterations.getAlterations().get(0).getDescription());
    }

    @Test
    public void whenProjectAlterationsListIsSetWithAlterationsListWithChangedElements_shouldMakeChangesToEntityAlterationsList() {
        //given
        List<Alteration> alterations = Stream.of(colorAlterModified, textAlter).collect(Collectors.toList());
        //when
        projectWithTextAndColorAlterations.setAlterations(alterations);
        //then
        assertEquals(2, projectWithTextAndColorAlterations.getAlterations().size());
        assertEquals("Red changed to magenta", projectWithTextAndColorAlterations.getAlterations().get(0).getDescription());
    }

    @Test
    public void whenProjectAlterationsListIsSetWithAlterationsListWithRemovedElements_shouldRemoveMissingElementFromEntityAlterationsList() {
        //given
        List<Alteration> alterations = Stream.of(textAlter).collect(Collectors.toList());
        //when
        projectWithTextAndColorAlterations.setAlterations(alterations);
        //then
        assertEquals(1, projectWithTextAndColorAlterations.getAlterations().size());
    }

    @Test
    public void whenProjectAlterationsListIsSetWithEmptyAlterationsList_shouldRemoveAllElementFromEntityAlterationsList() {
        //given
        List<Alteration> alterations = new ArrayList<>();
        //when
        projectWithTextAndColorAlterations.setAlterations(alterations);
        //then
        assertEquals(0, projectWithTextAndColorAlterations.getAlterations().size());
    }

}