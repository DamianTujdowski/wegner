package pl.wegner.documents.model.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wegner.documents.model.entities.ProductionOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductionOrderDtoTest {

    private ProductionOrderDto productionOrderDto;
    private AttributesDto attributesDto;
    private OrderDataDto orderDataDto;
    private InkDto inkDto;

    @BeforeEach
    public void setUp() {
        attributesDto = AttributesDto.builder()
                .operatorName("DT")
                .telephoneNumber("48606606606")
                .occasionalComments("Project contains widened pushbacks")
                .technicalComments("Do not add white widening")
                .build();

        inkDto = InkDto.builder()
                .id(1L)
                .symbol("Pantone 426")
                .build();

        List<InkDto> inkDtos = Stream.of(inkDto).collect(Collectors.toList());

        orderDataDto = OrderDataDto.builder()
                .id(2L)
                .inks(inkDtos)
                .build();

        List<OrderDataDto> orderDataDtos = Stream.of(orderDataDto).collect(Collectors.toList());

        productionOrderDto = ProductionOrderDto.builder()
                .id(1L)
                .designation("Zlecenie 12 05 2021")
                .occurrence(LocalDate.of(2021, 5, 12))
                .attributes(attributesDto)
                .orderDataDto(orderDataDtos)
                .build();
    }

    @Test
    public void productionOrderOrderDataList_shouldHaveSizeOne() {
        //given
        ProductionOrder productionOrder;
        //when
        productionOrder = productionOrderDto.map();
        //then
        assertEquals(productionOrder.getOrderData().size(), 1);
    }

    @Test
    public void productionOrderDesignation_shouldBeZlecenie_12_05_2021() {
        //given
        ProductionOrder productionOrder;
        //when
        productionOrder = productionOrderDto.map();
        //then
        assertEquals("Zlecenie 12 05 2021", productionOrder.getDesignation());
    }

}