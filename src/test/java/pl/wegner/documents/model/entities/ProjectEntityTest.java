package pl.wegner.documents.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class ProjectEntityTest {

    private Project projectWithNoInks, projectWithYelloMagentaInks;

    private Ink yellow, magenta, yellowModified, magentaModified;

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

        yellowModified = Ink.builder()
                .id(1)
                .symbol("Modified yellow")
                .projectId(1)
                .build();

        magentaModified = Ink.builder()
                .id(2)
                .symbol("Modified magenta")
                .projectId(1)
                .build();

        List<Ink> inks = Stream.of(yellow, magenta).collect(Collectors.toList());

        projectWithNoInks = Project.builder()
                .id(1)
                .designation("Alicja")
                .client("Komsomolec")
                .inks(new ArrayList<>())
                .build();

        projectWithYelloMagentaInks = Project.builder()
                .id(1)
                .designation("Alicja")
                .client("Komsomolec")
                .inks(inks)
                .build();
    }

    @Test
    public void whenProjectHasNoInks_shouldAddAllInks() {
        //given
        List<Ink> inks = Stream.of(yellow, magenta).collect(Collectors.toList());

        //when
        projectWithNoInks.setInks(inks);
        //then
        assertEquals(2, projectWithNoInks.getInks().size());
    }

    @Test
    public void whenProjectInksListIsSetWithIdenticalInksListTheSame_shouldNotMakeAnyChangesToEntityInksList() {
        //given
        List<Ink> inks = Stream.of(yellow, magenta).collect(Collectors.toList());

        //when
        projectWithYelloMagentaInks.setInks(inks);
        //then
        assertEquals(2, projectWithYelloMagentaInks.getInks().size());
        assertEquals("Yellow", projectWithYelloMagentaInks.getInks().get(0).getSymbol());
    }

    @Test
    public void whenProjectInksListIsSetWithInksListWithChangedElements_shouldMakeChangesToEntityInksList() {
        //given
        List<Ink> inks = Stream.of(yellow, magentaModified).collect(Collectors.toList());

        //when
        projectWithYelloMagentaInks.setInks(inks);
        //then
        assertEquals(2, projectWithYelloMagentaInks.getInks().size());
        assertEquals("Modified magenta", projectWithYelloMagentaInks.getInks().get(1).getSymbol());
    }

    @Test
    public void whenProjectInksListIsSetWithInksListWithRemovedElements_shouldRemoveMissingElementFromEntityInksList() {
        //given
        List<Ink> inks = Stream.of(yellow).collect(Collectors.toList());

        //when
        projectWithYelloMagentaInks.setInks(inks);
        //then
        assertEquals(1, projectWithYelloMagentaInks.getInks().size());
    }

    @Test
    public void whenProjectInksListIsSetWithEmptyInksList_shouldRemoveAllElementFromEntityInksList() {
        //given
        List<Ink> inks = new ArrayList<>();

        //when
        projectWithYelloMagentaInks.setInks(inks);
        //then
        assertEquals(0, projectWithYelloMagentaInks.getInks().size());
    }

}