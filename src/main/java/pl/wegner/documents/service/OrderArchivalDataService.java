package pl.wegner.documents.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.wegner.documents.model.dto.OrderArchivalDataDto;
import pl.wegner.documents.model.entities.OrderArchivalData;
import pl.wegner.documents.repository.OrderArchivalDataRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderArchivalDataService {

    private OrderArchivalDataRepository orderRepository;

    public OrderArchivalDataService(OrderArchivalDataRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderArchivalData findOrder(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Project with id %d does not exist", id)
        ));
    }

    public List<OrderArchivalData> findAll(int page, int size, Sort.Direction direction) {
        return orderRepository.findAllBy(PageRequest.of(page, size, Sort.by(direction, "occurrence")));
    }

    public OrderArchivalData save(OrderArchivalDataDto order) {
        OrderArchivalData newOrder = order.map();
        return orderRepository.save(newOrder);
    }

    @Transactional
    public OrderArchivalData edit(OrderArchivalDataDto order) {
        OrderArchivalData editedOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Project with id %d does not exist", order.getId())
                ));
        editedOrder.setOccurrence(order.getOccurrence());
        editedOrder.setFileName(order.getFileName());
        editedOrder.setProductionFileName(order.getProductionFileName());
        editedOrder.setPlatesDimensions(order.getPlatesDimensions());
        editedOrder.setPlatesQuantity(order.getPlatesQuantity());
        editedOrder.setLpi(order.getLpi());
        editedOrder.setSide(order.getSide());
//        editedOrder.setInks(order.getInks());
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
