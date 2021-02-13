package pl.wegner.documents.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProductionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String designation;

    private LocalDate occurrence;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "production_order_id")
    private List<OrderData> orderData;

    public void setOrderData(List<OrderData> orderData) {
        if (this.orderData == null) {
            this.orderData = orderData;
        } else {
            this.orderData.retainAll(orderData);
            orderData.removeAll(this.orderData);
            this.orderData.addAll(orderData);
        }
    }
}
