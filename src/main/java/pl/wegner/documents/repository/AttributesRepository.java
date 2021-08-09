package pl.wegner.documents.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wegner.documents.model.entities.Attributes;

import java.util.List;

@Repository
public interface AttributesRepository extends JpaRepository<Attributes, Long> {
    List<Attributes> findAllBy(Pageable pageable);
}