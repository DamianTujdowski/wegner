package pl.wegner.documents.service;

import org.springframework.stereotype.Service;
import pl.wegner.documents.model.entities.OrderDetailedData;
import pl.wegner.documents.repository.OrderDetailedDataRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class OrderDetailedDataService {

    private OrderDetailedDataRepository orderRepository;

    public OrderDetailedDataService(OrderDetailedDataRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDetailedData findOrder(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Project with id %d does not exist", id)));
    }
}
