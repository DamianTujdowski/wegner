package pl.wegner.documents.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.wegner.documents.model.entities.Proof;
import pl.wegner.documents.repository.ProofRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProofService {

    private ProofRepository repository;

    public ProofService(ProofRepository repository) {
        this.repository = repository;
    }

    public Proof findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Proof with id %d does not exist", id)
                ));
    }

    public List<Proof> findAll(int page, int size) {
        return repository.findAllBy(PageRequest.of(page, size));
    }

    public Proof save(Proof proof) {
        return repository.save(proof);
    }

    public Proof edit(Proof proof) {
        Proof edited = repository.findById(proof.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Proof with id %d does not exist", proof.getId())
                ));
        edited.setName(proof.getName());
        edited.setQuantity(proof.getQuantity());
        edited.setPrincipal(proof.getPrincipal());
        edited.setDimension(proof.getDimension());
        edited.setSendMethod(proof.getSendMethod());
        edited.setPayer(proof.getPayer());
        return edited;
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
