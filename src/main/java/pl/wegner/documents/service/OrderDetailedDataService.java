package pl.wegner.documents.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.wegner.documents.model.entities.OrderDetailedData;
import pl.wegner.documents.repository.OrderDetailedDataRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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

    public List<OrderDetailedData> findAll(int page, int size) {
        return orderRepository.findAllBy(PageRequest.of(page, size));
    }

    public OrderDetailedData save(OrderDetailedData order) {
        return orderRepository.save(order);
    }

    public OrderDetailedData edit(OrderDetailedData order) {
        OrderDetailedData editedOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Project with id %d does not exist", order.getId())
                ));
        editedOrder.setDate(order.getDate());
        editedOrder.setFileName(order.getFileName());
        editedOrder.setProductionFileName(order.getProductionFileName());
        editedOrder.setDimensions(order.getDimensions());
        editedOrder.setPlatesNumber(order.getPlatesNumber());
        editedOrder.setLpi(order.getLpi());
        editedOrder.setSide(order.getSide());
        editedOrder.setInks(order.getInks());
        editedOrder.setNotes(order.getNotes());
        editedOrder.setPayer(order.getPayer());
        editedOrder.setPrintHouse(order.getPrintHouse());
        editedOrder.setPlatesFactory(order.getPlatesFactory());
        editedOrder.setPlateThickness(order.getPlateThickness());
        return editedOrder;
    }

    public void delete(long id) {
        orderRepository.deleteById(id);
    }
}
