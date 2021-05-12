package pl.wegner.documents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.entities.Attributes;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.model.entities.ProductionOrder;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderDto implements Mappable<ProductionOrder> {

    @PositiveOrZero(message = "can't be lower than 0")
    private long id;

    @NotBlank(message = "must be provided")
    @Size(min = 19, max = 23, message = "length must be between 19 and 23 characters")
    @Pattern(regexp = "(Zlecenie|zlecenie) \\d{2} \\d{2} \\d{4} ?(V2)?",
            message = "must be formatted like -Zlecenie yyyy mm dd- with optional version mark")
    private String designation;

    @PastOrPresent(message = "mustn't be in the future")
    private LocalDate occurrence;

    @NotNull(message = "must be provided")
    private Attributes attributes;

    @NotEmpty(message = "must be provided and must contain at least one element")
    private List<OrderData> orderData;

    @Override
    public ProductionOrder map() {
        return ProductionOrder.builder()
                .designation(this.designation)
                .occurrence(this.occurrence)
                .attributes(this.attributes)
                .orderData(this.orderData)
                .build();

    }
}
