package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.wegner.documents.model.entities.OrderDetailedData;
import pl.wegner.documents.service.OrderDetailedDataService;

@RestController
public class OrderDetailedDataController {

    private OrderDetailedDataService orderService;

    public OrderDetailedDataController(OrderDetailedDataService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{id}")
    public OrderDetailedData findDetailedOrder(@PathVariable int id) {
        return orderService.findOrder(id);
    }
}
