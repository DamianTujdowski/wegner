package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.dto.OrderDataDto;
import pl.wegner.documents.model.entities.OrderArchivalData;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.service.OrderDataService;

import javax.validation.Valid;

@RestController
public class OrderDataController {

    private OrderDataService service;

    public OrderDataController(OrderDataService service) {
        this.service = service;
    }

    @GetMapping("/data/{id}")
    public OrderData getOrder(@PathVariable int id) {
        return service.findById(id);
    }

    // only generates orderDataDto does not save it to DB
    @PostMapping("/data/gen")
    public OrderData generateOrderData(@RequestBody OrderArchivalData archivalData) {
        return service.generateData(archivalData);
    }

    @PostMapping("/data/")
    public OrderData save(@Valid @RequestBody OrderDataDto data) {
        return service.save(data);
    }

    @PutMapping("/data/")
    public OrderData edit(@Valid @RequestBody OrderDataDto data) {
        return service.edit(data);
    }

    @DeleteMapping("/data/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
