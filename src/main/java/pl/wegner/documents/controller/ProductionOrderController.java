package pl.wegner.documents.controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.dto.ProductionOrderDto;
import pl.wegner.documents.model.entities.ProductionOrder;
import pl.wegner.documents.service.ProductionOrderService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductionOrderController {

    private ProductionOrderService orderService;

    public ProductionOrderController(ProductionOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{id}")
    public ProductionOrder findById(@PathVariable long id) {
        return orderService.findById(id);
    }

    @GetMapping("/orders/")
    public List<ProductionOrder> findAll(@RequestParam int page,
                                         @RequestParam(defaultValue = "20") int size,
                                         @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return orderService.findAll(page, size, direction);
    }

    @PostMapping("/orders/")
    public ProductionOrder save(@Valid @RequestBody ProductionOrderDto order) {
        return orderService.save(order);
    }

    @PutMapping("/orders/")
    public ProductionOrder edit(@Valid @RequestBody ProductionOrderDto order) {
        return orderService.edit(order);
    }

    @DeleteMapping("/orders/{id}")
    public void delete(@PathVariable long id) {
        orderService.delete(id);
    }

}
