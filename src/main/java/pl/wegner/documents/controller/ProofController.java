package pl.wegner.documents.controller;

import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.entities.Proof;
import pl.wegner.documents.service.ProofService;

import java.util.List;

@RestController
public class ProofController {

    private ProofService proofService;

    public ProofController(ProofService service) {
        this.proofService = service;
    }

    @GetMapping("/proofs/{id}")
    public Proof findById(long id) {
        return proofService.findById(id);
    }

    @GetMapping("/proofs/")
    public List<Proof> findAll(@RequestParam int page,
                               @RequestParam(defaultValue = "20") int size) {
        return proofService.findAll(page, size);
    }

    @PostMapping("/proofs/")
    public Proof save(@RequestBody Proof proof) {
        return proofService.save(proof);
    }

    @PutMapping("/proofs/")
    public Proof edit(@RequestBody Proof proof) {
        return proofService.edit(proof);
    }

    @DeleteMapping("/proofs/")
    public void delete(long id) {
        proofService.delete(id);
    }


}
