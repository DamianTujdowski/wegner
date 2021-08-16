package pl.wegner.documents.model.dto;

import lombok.*;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.model.entities.ProductionOrder;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderDto implements Mappable<ProductionOrder> {

    @Positive(message = "can't be lower than 1")
    private Long id;

    @NotBlank(message = "must be provided")
    @Size(min = 19, max = 23, message = "length must be between 19 and 23 characters")
    @Pattern(regexp = "[Zz]lecenie \\d{2} \\d{2} \\d{4}( [Vv]2)?",
            message = "must be formatted like -Zlecenie dd mm yyyy- with optional version mark")
    private String designation;

    @PastOrPresent(message = "mustn't be in the future")
    private LocalDate occurrence;

    @Valid
    @NotNull(message = "must be provided")
    private AttributesDto attributes;

    @NotEmpty(message = "must be provided and must contain at least one element")
    private List<@Valid OrderDataDto> orderDataDto;

    //TODO test below method if attributes.map() method works as expected
    @Override
    public ProductionOrder map() {
        return ProductionOrder.builder()
                .designation(this.designation)
                .occurrence(this.occurrence)
                .attributes(this.attributes.map())
                .orderData(mapToOrderData())
                .build();
    }

    public List<OrderData> mapToOrderData() {
        return this.orderDataDto
                .stream()
                .map(OrderDataDto::map)
                .collect(Collectors.toList());
    }
}
