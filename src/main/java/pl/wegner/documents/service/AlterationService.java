package pl.wegner.documents.service;

import org.springframework.stereotype.Service;
import pl.wegner.documents.model.dto.AlterationDto;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.repository.AlterationRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class AlterationService {

    private AlterationRepository repository;

    public AlterationService(AlterationRepository repository) {
        this.repository = repository;
    }

    public Alteration save(AlterationDto alterationDto) {
        Alteration newAlteration = alterationDto.map();
        return repository.save(newAlteration);
    }

    @Transactional
    public Alteration edit(AlterationDto alterationDto) {
        Alteration edited = repository.findById(alterationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Alteration with id %d does not exist", alterationDto.getId())
                ));
        edited.setOccurrence(alterationDto.getOccurrence());
        edited.setDescription(alterationDto.getDescription());
        edited.setDuration(alterationDto.getDuration());
        edited.setProjectId(alterationDto.getProjectId());
        return edited;
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

}
