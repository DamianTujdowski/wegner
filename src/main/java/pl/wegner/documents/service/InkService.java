package pl.wegner.documents.service;

import org.springframework.stereotype.Service;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.repository.InkRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class InkService {

    private InkRepository repository;

    public InkService(InkRepository repository) {
        this.repository = repository;
    }

    public Ink save(Ink ink) {
        return repository.save(ink);
    }

    public Ink edit(Ink ink) {
        Ink edited = repository.findById(ink.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Ink with id %d does not exist", ink.getId())
                ));
        edited.setName(ink.getName());
        edited.setAngle(ink.getAngle());
        edited.setLpi(ink.getLpi());
        edited.setProjectId(ink.getProjectId());
        edited.setOrderDataId(ink.getOrderDataId());
        edited.setOrderArchivalDataId(ink.getOrderArchivalDataId());
        return edited;
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
