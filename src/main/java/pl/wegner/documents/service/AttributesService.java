package pl.wegner.documents.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wegner.documents.model.dto.AttributesDto;
import pl.wegner.documents.model.entities.Attributes;
import pl.wegner.documents.repository.AttributesRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AttributesService {

    private AttributesRepository attributesRepository;

    public AttributesService(AttributesRepository attributesRepository) {
        this.attributesRepository = attributesRepository;
    }

    public Attributes findById(Long id) {
        return attributesRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Attributes with id %d do not exist", id)
                ));
    }

    public List<Attributes> findAll(int page, int size, Sort.Direction direction) {
        return attributesRepository.findAllBy(PageRequest.of(page, size, Sort.by(direction, "id")));
    }

    public Attributes save(AttributesDto attributes) {
        Attributes newAttributes = attributes.map();
        return attributesRepository.save(newAttributes);
    }

    //TODO test that method
    @Transactional
    public Attributes edit(AttributesDto attributes) {
        Attributes edited = attributesRepository.findById(attributes.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Attributes with id %d do not exist", attributes.getId())
                ));
        BeanUtils.copyProperties(attributes,edited);
        return edited;
    }

    public void delete(Long id) {
        attributesRepository.deleteById(id);
    }
}
