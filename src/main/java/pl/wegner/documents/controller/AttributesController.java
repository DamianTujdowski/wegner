package pl.wegner.documents.controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.dto.AttributesDto;
import pl.wegner.documents.model.entities.Attributes;
import pl.wegner.documents.service.AttributesService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AttributesController {

    private AttributesService service;

    public AttributesController(AttributesService service) {
        this.service = service;
    }

    @GetMapping("/attributes/{id}")
    public Attributes getAttributes(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/attributes/")
    public List<Attributes> findAllAttributes(@RequestParam int page,
                                              @RequestParam(defaultValue = "20") int size,
                                              @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return service.findAll(page, size, direction);
    }

    @PostMapping("/attributes/")
    public Attributes save(@Valid @RequestBody AttributesDto attributesDto) {
        return service.save(attributesDto);
    }

    @PutMapping("/attributes/")
    public Attributes edit(@Valid @RequestBody AttributesDto attributesDto) {
        return service.edit(attributesDto);
    }

    @DeleteMapping("/attributes/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
