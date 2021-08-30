package pl.wegner.documents.service;

import org.springframework.stereotype.Service;
import pl.wegner.documents.model.dto.OrderArchivalDataDto;
import pl.wegner.documents.model.dto.OrderDataDto;
import pl.wegner.documents.model.entities.OrderArchivalData;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.repository.OrderDataRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class OrderDataService {

    private OrderDataRepository repository;

    public OrderDataService(OrderDataRepository repository) {
        this.repository = repository;
    }

    public OrderData findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order data with id %d does not exist", id)
                ));
    }

    public OrderData generateData(OrderArchivalDataDto archivalData) {
        return archivalData.mapToOrderData();
    }

    public OrderData save(OrderDataDto data) {
        OrderData newOrder = data.map();
        return repository.save(newOrder);
    }

    //TODO refactor updating entities - remove setters, add service superclass with method validating if entity exists in db
    @Transactional
    public OrderData edit(OrderDataDto data) {
        OrderData edited = repository.findById(data.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order data with id %d does not exist", data.getId())
                ));
        edited.setFileName(data.getFileName());
        edited.setPlatesDimensions(data.getPlatesDimensions());
        edited.setPlatesQuantity(data.getPlatesQuantity());
//this line is throwing error - this method is going to be refactored anyway
        //        edited.setInks(data.getInks());
        edited.setPlateThickness(data.getPlateThickness());
        edited.setSide(data.getSide());
        edited.setNotes(data.getNotes());
        edited.setProductionOrderId(data.getProductionOrderId());
        return edited;
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
