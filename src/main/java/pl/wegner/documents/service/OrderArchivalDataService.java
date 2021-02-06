package pl.wegner.documents.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.wegner.documents.model.entities.OrderArchivalData;
import pl.wegner.documents.repository.OrderArchivalDataRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderArchivalDataService {

    private OrderArchivalDataRepository orderRepository;

    public OrderArchivalDataService(OrderArchivalDataRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderArchivalData findOrder(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Project with id %d does not exist", id)));
    }

    public List<OrderArchivalData> findAll(int page, int size) {
        return orderRepository.findAllBy(PageRequest.of(page, size));
    }

    public OrderArchivalData save(OrderArchivalData order) {
        return orderRepository.save(order);
    }

    public OrderArchivalData edit(OrderArchivalData order) {
        OrderArchivalData editedOrder = orderRepository.findById(order.getId())
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
