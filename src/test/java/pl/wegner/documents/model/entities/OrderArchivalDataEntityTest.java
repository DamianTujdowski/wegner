package pl.wegner.documents.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderArchivalDataEntityTest {

    private OrderArchivalData dataWithNoInks, dataWithYellowMagentaInks;

    private Ink yellow, magenta, magentaModified;

    @BeforeEach
    void init() {
        yellow = Ink.builder()
                .id(1L)
                .symbol("Yellow")
                .projectId(1L)
                .build();

        magenta = Ink.builder()
                .id(2L)
                .symbol("Magenta")
                .projectId(1L)
                .build();

        List<Ink> inks = Stream.of(yellow, magenta).collect(Collectors.toList());

        magentaModified = Ink.builder()
                .id(2L)
                .symbol("Modified magenta")
                .projectId(1L)
                .build();

        dataWithNoInks = OrderArchivalData.builder()
                .fileName("Palstmoroz many plates")
                .platesDimensions("200x500")
                .inks(new ArrayList<>())
                .build();

        dataWithYellowMagentaInks = OrderArchivalData.builder()
                .fileName("Palstmoroz many plates")
                .platesDimensions("200x500")
                .inks(inks)
                .build();
    }

    @Test
    void whenArchOrderHasNoData_shouldAddAllData() {
        //given
        List<Ink> inks = Stream.of(yellow, magenta).collect(Collectors.toList());
        //when
        dataWithNoInks.setInks(inks);
        //then
        assertEquals(2, dataWithNoInks.getInks().size());
    }

    @Test
    void whenArchOrderDataListIsSetWithIdenticalDataList_shouldNotMakeAnyChangesToEntityDataList() {
        //given
        List<Ink> inks = Stream.of(yellow, magenta).collect(Collectors.toList());
        //when
        dataWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(2, dataWithYellowMagentaInks.getInks().size());
        assertEquals("Yellow", dataWithYellowMagentaInks.getInks().get(0).getSymbol());
    }

    @Test
    void whenArchOrderDataListIsSetWithDataListWithChangedElements_shouldMakeChangesToEntityDataList() {
        //given
        List<Ink> inks = Stream.of(yellow, magentaModified).collect(Collectors.toList());
        //when
        dataWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(2, dataWithYellowMagentaInks.getInks().size());
        assertEquals("Modified magenta", dataWithYellowMagentaInks.getInks().get(1).getSymbol());
    }

    @Test
    void whenArchOrderDataListIsSetWithDataListWithRemovedElements_shouldRemoveMissingElementFromEntityDataList() {
        //given
        List<Ink> inks = Stream.of(yellow).collect(Collectors.toList());
        //when
        dataWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(1, dataWithYellowMagentaInks.getInks().size());
    }

    @Test
    void whenArchOrderDataListIsSetWithEmptyDataList_shouldRemoveAllElementFromEntityDataList() {
        //given
        List<Ink> inks = new ArrayList<>();
        //when
        dataWithYellowMagentaInks.setInks(inks);
        //then
        assertEquals(0, dataWithYellowMagentaInks.getInks().size());
    }



}