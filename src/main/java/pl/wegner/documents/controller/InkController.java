package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.*;

import pl.wegner.documents.model.dto.InkDto;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.service.InkService;

import javax.validation.Valid;

@RestController
public class InkController {

    private InkService service;

    public InkController(InkService service) {
        this.service = service;
    }

    @PostMapping("/inks/")
    public Ink save(@Valid @RequestBody InkDto ink) {
        return service.save(ink);
    }

    @PutMapping("/inks/")
    public Ink edit(@Valid @RequestBody InkDto ink) {
        return service.edit(ink);
    }

    @DeleteMapping("/inks/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
