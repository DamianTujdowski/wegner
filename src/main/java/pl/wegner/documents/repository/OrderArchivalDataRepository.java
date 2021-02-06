package pl.wegner.documents.repository;

import pl.wegner.documents.model.entities.OrderArchivalData;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderArchivalDataRepository extends JpaRepository<OrderArchivalData, Long> {

    List<OrderArchivalData> findAllBy(Pageable pageable);

}
