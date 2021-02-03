package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.entities.OrderDetailedData;
import pl.wegner.documents.model.entities.Project;
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

    @GetMapping("/orders/")
    public OrderDetailedData findDetailedOrder(@RequestParam int page,
                                               @RequestParam(defaultValue = "20") int size) {
        return orderService.findAll(page, size);
    }

    @PostMapping("/orders/")
    public OrderDetailedData save(@RequestBody OrderDetailedData order) {
        return orderService.save(order);
    }

    @PutMapping("/orders/")
    public OrderDetailedData edit(@RequestBody OrderDetailedData order) {
        return orderService.edit(order);
    }

    @DeleteMapping("/orders/")
    public void delete(long id) {
        orderService.delete(id);
    }


}
