package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.dto.AlterationDto;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.service.AlterationService;

import javax.validation.Valid;

@RestController
public class AlterationController {

    private AlterationService service;

    public AlterationController(AlterationService service) {
        this.service = service;
    }

    @PostMapping("/alterations/")
    public Alteration save(@Valid @RequestBody AlterationDto alterationDto) {
        return service.save(alterationDto);
    }

    @PutMapping("/alterations/")
    public Alteration edit(@Valid @RequestBody AlterationDto alterationDto) {
        return service.edit(alterationDto);
    }

    @DeleteMapping("/alterations/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
