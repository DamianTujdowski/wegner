package pl.wegner.documents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.model.entities.Attributes;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.model.entities.ProductionOrder;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductionOrderServiceTest {

    private ProductionOrder order;
    private Attributes attributes;
    private OrderData orderData;

    @BeforeEach
    public void setUp() {
        attributes = Attributes.builder()
                .occasionalComments("Bigger logo")
                .technicalComments("No bump up")
                .build();
        orderData = OrderData.builder()
                .fileName("Locust star")
                .platesQuantity(4)
                .build();
        order = ProductionOrder.builder()
                .designation("Zlecenie 11 12 2012")
                .attributes(attributes)
                .orderData(Collections.singletonList(orderData))
                .build();
    }

    @Test
    public void shouldChangeAttributesTechnicalComments() {
        System.out.println(attributes.getTechnicalComments());
        attributes.setTechnicalComments("Do not use fabric compensation");
        assertEquals("Do not use fabric compensation", order.getAttributes().getTechnicalComments());
    }

}