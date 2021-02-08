package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.*;

import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.service.InkService;

@RestController
public class InkController {

    private InkService service;

    public InkController(InkService service) {
        this.service = service;
    }

    @PostMapping("/inks/")
    public Ink save(@RequestBody Ink ink) {
        return service.save(ink);
    }

    @PutMapping("/inks/")
    public Ink edit(@RequestBody Ink ink) {
        return service.edit(ink);
    }

    @DeleteMapping("/inks/")
    public void delete(long id) {
        service.delete(id);
    }
}
