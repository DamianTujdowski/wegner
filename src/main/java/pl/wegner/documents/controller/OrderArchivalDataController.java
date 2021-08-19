package pl.wegner.documents.controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.dto.OrderArchivalDataDto;
import pl.wegner.documents.model.entities.OrderArchivalData;
import pl.wegner.documents.service.OrderArchivalDataService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderArchivalDataController {

    private OrderArchivalDataService orderService;

    public OrderArchivalDataController(OrderArchivalDataService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/archives/{id}")
    public OrderArchivalData findDetailedOrder(@PathVariable int id) {
        return orderService.findOrder(id);
    }

    @GetMapping("/archives/")
    public List<OrderArchivalData> findDetailedOrder(@RequestParam int page,
                                                     @RequestParam(defaultValue = "20") int size,
                                                     @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return orderService.findAll(page, size, direction);
    }

    @PostMapping("/archives/")
    public OrderArchivalData save(@Valid @RequestBody OrderArchivalDataDto order) {
        return orderService.save(order);
    }

    @PutMapping("/archives/")
    public OrderArchivalData edit(@Valid @RequestBody OrderArchivalDataDto order) {
        return orderService.edit(order);
    }

    @DeleteMapping("/archives/{id}")
    public void delete(@PathVariable long id) {
        orderService.delete(id);
    }


}
