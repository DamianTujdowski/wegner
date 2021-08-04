package pl.wegner.documents.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductionOrderTest {

    private ProductionOrder order;
    private OrderData first, second, third, fourth;

    @BeforeEach
    public void setUp() {
        first = OrderData.builder()
                .fileName("Zlecenie 00 00 0000")
                .build();
        second = OrderData.builder()
                .fileName("Zlecenie 11 11 1111")
                .build();
        third = OrderData.builder()
                .fileName("Zlecenie 22 22 2222")
                .build();
        fourth = OrderData.builder()
                .fileName("Zlecenie 33 33 3333")
                .build();

        List<OrderData> orderData = Arrays.asList(first, second, third, fourth);

        order = ProductionOrder.builder()
                .designation("Very important order")
                .orderData(orderData)
                .build();
    }


    @Test
    public void shouldUpdateOrderData() {
        second.setFileName("Zlecenie 11 11 1111 zlecenie");

        String updateResult = order.getOrderData().get(1).getFileName();

        assertEquals("Zlecenie 11 11 1111 zlecenie", updateResult);
    }

}