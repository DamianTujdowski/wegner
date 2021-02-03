package pl.wegner.documents.repository;

import pl.wegner.documents.model.entities.OrderDetailedData;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailedDataRepository extends JpaRepository<OrderDetailedData, Long> {

    OrderDetailedData findAllBy(Pageable pageable);

}
