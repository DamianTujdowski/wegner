package pl.wegner.documents.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wegner.documents.model.enums.LinesPerInch;
import pl.wegner.documents.model.enums.PrintSide;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductionOrderEntityTest {

    private ProductionOrder orderWithNoData, orderWithTwoElements;

    private OrderData germanProjectOrder, polishProjectOrder, modifiedGermanProjectOrder;

    @BeforeEach
    public void setUp() {

        germanProjectOrder = OrderData.builder()
                .fileName("Pfeffer hunde prings")
                .lpi(LinesPerInch.HIGHER_MEDIUM)
                .notes("Thin white lines on black background")
                .side(PrintSide.OUTER)
                .build();

        polishProjectOrder = OrderData.builder()
                .fileName("Nasiona sezamu dla hipsterow")
                .lpi(LinesPerInch.LOW)
                .notes("Scattered raster points")
                .side(PrintSide.INNER)
                .build();

        List<OrderData> data = Stream.of(germanProjectOrder, polishProjectOrder).collect(Collectors.toList());

        modifiedGermanProjectOrder = OrderData.builder()
                .fileName("Pfeffer hunde stinkishe lungen")
                .lpi(LinesPerInch.HIGHER_MEDIUM)
                .notes("Thin white lines on black background")
                .side(PrintSide.OUTER)
                .build();

        orderWithNoData = ProductionOrder.builder()
                .occurrence(LocalDate.of(2021, 2, 13))
                .designation("13 02 2021 order")
                .orderData(new ArrayList<>())
                .build();

        orderWithTwoElements = ProductionOrder.builder()
                .occurrence(LocalDate.of(2021, 1, 11))
                .designation("11 01 2021 order")
                .orderData(data)
                .build();
    }

    @Test
    public void whenOrderHasNoData_shouldAddAllData() {
        //given
        List<OrderData> data = Stream.of(germanProjectOrder, polishProjectOrder).collect(Collectors.toList());
        //when
        orderWithNoData.setOrderData(data);
        //then
        assertEquals(2, orderWithNoData.getOrderData().size());
    }

    @Test
    public void whenOrderDataListIsSetWithIdenticalDataList_shouldNotMakeAnyChangesToEntityDataList() {
        //given
        List<OrderData> data = Stream.of(germanProjectOrder, polishProjectOrder).collect(Collectors.toList());
        //when
        orderWithTwoElements.setOrderData(data);
        //then
        assertEquals(2, orderWithTwoElements.getOrderData().size());
        assertEquals("Pfeffer hunde prings", orderWithTwoElements.getOrderData().get(0).getFileName());
    }

    @Test
    public void whenOrderDataListIsSetWithDataListWithChangedElements_shouldMakeChangesToEntityDataList() {
        //given
        List<OrderData> data = Stream.of(modifiedGermanProjectOrder, polishProjectOrder).collect(Collectors.toList());
        //when
        orderWithTwoElements.setOrderData(data);
        //then
        assertEquals(2, orderWithTwoElements.getOrderData().size());
        assertEquals("Pfeffer hunde stinkishe lungen", orderWithTwoElements.getOrderData().get(1).getFileName());
    }

    @Test
    public void whenOrderDataListIsSetWithDataListWithRemovedElements_shouldRemoveMissingElementFromEntityDataList() {
        //given
        List<OrderData> data = Stream.of(polishProjectOrder).collect(Collectors.toList());
        //when
        orderWithTwoElements.setOrderData(data);
        //then
        assertEquals(1, orderWithTwoElements.getOrderData().size());
    }

    @Test
    public void whenOrderDataListIsSetWithEmptyDataList_shouldRemoveAllElementFromEntityDataList() {
        //given
        List<OrderData> data = new ArrayList<>();
        //when
        orderWithTwoElements.setOrderData(data);
        //then
        assertEquals(0, orderWithTwoElements.getOrderData().size());
    }



}