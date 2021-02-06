package pl.wegner.documents.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wegner.documents.model.entities.ProductionOrder;

import java.util.List;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Long> {

    List<ProductionOrder> findAllBy(Pageable pageable);
}
