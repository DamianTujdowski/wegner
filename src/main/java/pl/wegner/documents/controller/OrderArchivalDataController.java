package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.entities.OrderArchivalData;
import pl.wegner.documents.service.OrderArchivalDataService;

import java.util.List;

@RestController
public class OrderArchivalDataController {

    private OrderArchivalDataService orderService;

    public OrderArchivalDataController(OrderArchivalDataService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("//{id}")
    public OrderArchivalData findDetailedOrder(@PathVariable int id) {
        return orderService.findOrder(id);
    }

    @GetMapping("/orders/")
    public List<OrderArchivalData> findDetailedOrder(@RequestParam int page,
                                                     @RequestParam(defaultValue = "20") int size) {
        return orderService.findAll(page, size);
    }

    @PostMapping("/orders/")
    public OrderArchivalData save(@RequestBody OrderArchivalData order) {
        return orderService.save(order);
    }

    @PutMapping("/orders/")
    public OrderArchivalData edit(@RequestBody OrderArchivalData order) {
        return orderService.edit(order);
    }

    @DeleteMapping("/orders/")
    public void delete(long id) {
        orderService.delete(id);
    }


}
