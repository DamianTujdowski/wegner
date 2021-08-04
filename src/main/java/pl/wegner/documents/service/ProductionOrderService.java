package pl.wegner.documents.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.wegner.documents.model.dto.ProductionOrderDto;
import pl.wegner.documents.model.entities.ProductionOrder;
import pl.wegner.documents.repository.ProductionOrderRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductionOrderService {

    private ProductionOrderRepository orderRepository;

    public ProductionOrderService(ProductionOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ProductionOrder findById(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Production order with id %d does not exist", id)
                ));
    }

    public List<ProductionOrder> findAll(int page, int size, Sort.Direction direction) {
        return orderRepository.findAllBy(PageRequest.of(page, size, Sort.by(direction, "occurrence")));
    }

    public ProductionOrder save(ProductionOrderDto order) {
        ProductionOrder newProductionOrder = order.map();
        return orderRepository.save(newProductionOrder);
    }

    @Transactional
    public ProductionOrder edit(ProductionOrderDto order) {
        ProductionOrder edited = orderRepository.findById(order.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Production order with id %d does not exist", order.getId())
                ));
        edited.setDesignation(order.getDesignation());
        edited.setOccurrence(order.getOccurrence());
        edited.setAttributes(order.getAttributes().map());
        edited.setOrderData(order.mapToOrderData());
        return edited;
    }

    public void delete(long id) {
        orderRepository.deleteById(id);
    }
}
