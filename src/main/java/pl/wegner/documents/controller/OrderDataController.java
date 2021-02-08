package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.service.OrderDataService;

@RestController
public class OrderDataController {

    private OrderDataService service;

    public OrderDataController(OrderDataService service) {
        this.service = service;
    }

    @PostMapping("/data/")
    public OrderData save(@RequestBody OrderData data) {
        return service.save(data);
    }

    @PutMapping("/data/")
    public OrderData edit(@RequestBody OrderData data) {
        return service.edit(data);
    }

    @DeleteMapping("/data/")
    public void delete(long id) {
        service.delete(id);
    }

}
