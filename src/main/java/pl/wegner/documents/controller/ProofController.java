package pl.wegner.documents.controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.dto.ProofDto;
import pl.wegner.documents.model.entities.Proof;
import pl.wegner.documents.service.ProofService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProofController {

    private ProofService proofService;

    public ProofController(ProofService service) {
        this.proofService = service;
    }

    @GetMapping("/proofs/{id}")
    public Proof findById(@PathVariable long id) {
        return proofService.findById(id);
    }

    @GetMapping("/proofs/")
    public List<Proof> findAll(@RequestParam int page,
                               @RequestParam(defaultValue = "20") int size,
                               @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return proofService.findAll(page, size, direction);
    }

    @PostMapping("/proofs/")
    public Proof save(@Valid @RequestBody ProofDto proof) {
        return proofService.save(proof);
    }

    @PutMapping("/proofs/")
    public Proof edit(@Valid @RequestBody ProofDto proof) {
        return proofService.edit(proof);
    }

    @DeleteMapping("/proofs/{id}")
    public void delete(@PathVariable long id) {
        proofService.delete(id);
    }


}
