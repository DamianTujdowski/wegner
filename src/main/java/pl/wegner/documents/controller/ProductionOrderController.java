package pl.wegner.documents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.entities.ProductionOrder;
import pl.wegner.documents.service.ProductionOrderService;

import java.util.List;

@Controller
public class ProductionOrderController {

    private ProductionOrderService orderService;

    public ProductionOrderController(ProductionOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{id}")
    public ProductionOrder findProductionOrder(long id){
        return orderService.findById(id);
    }

    @GetMapping("/orders/")
    public List<ProductionOrder> findProductionOrder(@RequestParam int page,
                                                     @RequestParam(defaultValue = "20") int size){
        return orderService.findAll(page, size);
    }

    @PostMapping("/orders/")
    public ProductionOrder save(@RequestBody ProductionOrder order) {
        return orderService.save(order);
    }

    @PutMapping("/orders/")
    public ProductionOrder edit(@RequestBody ProductionOrder order) {
        return orderService.edit(order);
    }

    @DeleteMapping("/orders/")
    public void delete(long id) {
        orderService.delete(id);
    }


}
