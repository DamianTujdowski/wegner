package pl.wegner.documents.repository;

import pl.wegner.documents.model.entities.OrderDetailedData;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailedDataRepository extends JpaRepository<OrderDetailedData, Long> {

    List<OrderDetailedData> findAllBy(Pageable pageable);

}
