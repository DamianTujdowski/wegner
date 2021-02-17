package pl.wegner.documents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.entities.OrderArchivalData;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.model.enums.LinesPerInch;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.repository.OrderDataRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class OrderDataServiceTest {

    @TestConfiguration
    static class OrderDataServiceContextConfiguration{

        OrderDataRepository repository;

        @Bean
        public OrderDataService dataService() {
            return new OrderDataService(repository);
        }

    }

    @Autowired
    private OrderDataService service;

    private OrderArchivalData archivalData;

    private OrderData orderData;

    @BeforeEach
    public void init() {
        List<Ink> inks = new ArrayList<>();
        archivalData = OrderArchivalData.builder()
                .occurrence(LocalDate.of(2020, 10, 3))
                .fileName("Alicja 03 10 2020")
                .productionFileName("Alicja siemie 150g 03 10 2020 skrot")
                .platesDimensions("240x450")
                .platesQuantity(4)
                .lpi(LinesPerInch.MEDIUM)
                .side(PrintSide.INNER)
                .inks(inks)
                .notes("Jedna matryca wznowiona")
                .payer("Ryszard Klimczak")
                .printHouse("Palstmoroz")
                .platesFactory("Fleksograf")
                .plateThickness(PlateThickness.THIN)
                .build();

        orderData = OrderData.builder()
                .fileName("Alicja 03 10 2020")
                .platesDimensions("240x450")
                .platesQuantity(4)
                .lpi(LinesPerInch.MEDIUM)
                .inks(inks)
                .plateThickness(PlateThickness.THIN)
                .side(PrintSide.INNER)
                .build();
    }

    @Test
    public void shouldCorrectlyGenerateOrderDataFromArchivalOrderData() {
        //given
        OrderData generatedData;
        //when
        generatedData = service.generateData(archivalData);
        //then
        assertEquals(orderData, generatedData);
    }


}