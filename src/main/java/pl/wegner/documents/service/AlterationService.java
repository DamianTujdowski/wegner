package pl.wegner.documents.service;

import org.springframework.stereotype.Service;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.repository.AlterationRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class AlterationService {

    private AlterationRepository repository;

    public AlterationService(AlterationRepository repository) {
        this.repository = repository;
    }


    public Alteration save(Alteration alteration) {
        return repository.save(alteration);
    }

    public Alteration edit(Alteration alteration) {
        Alteration edited = repository.findById(alteration.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Alteration with id %d does not exist", alteration.getId())
                ));
        edited.setOccurrence(alteration.getOccurrence());
        edited.setDescription(alteration.getDescription());
        edited.setDuration(alteration.getDuration());
        edited.setProjectId(alteration.getProjectId());
        return edited;
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

}
