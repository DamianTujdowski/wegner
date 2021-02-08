package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.service.AlterationService;

@RestController
public class AlterationController {

    private AlterationService service;

    public AlterationController(AlterationService service) {
        this.service = service;
    }

    @PostMapping("/alterations/")
    public Alteration save(@RequestBody Alteration alteration) {
        return service.save(alteration);
    }

    @PutMapping("/alterations/")
    public Alteration edit(@RequestBody Alteration alteration) {
        return service.edit(alteration);
    }

    @DeleteMapping("/alterations/")
    public void delete(long id) {
        service.delete(id);
    }
}
