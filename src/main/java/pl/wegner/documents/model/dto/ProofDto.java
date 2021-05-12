package pl.wegner.documents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.entities.Proof;
import pl.wegner.documents.model.enums.SendMethod;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProofDto implements Mappable<Proof> {

    @PositiveOrZero(message = "can't be lower than 0")
    private long id;

    @PastOrPresent(message = "mustn't be in the future")
    private LocalDate printDate;

    @NotBlank(message = "must be provided")
    @Size(min = 10, max = 50, message = "length must be between 10 and 50 characters")
    @Pattern(regexp = "[0-9aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ ]*",
            message = "can contain only Polish letters, numbers and space")
    private String designation;

    @Min(value = 1, message = "minimal quantity is 1")
    @Max(value = 10, message = "maximal quantity is 10")
    private int quantity;

    @NotBlank(message = "must be provided")
    @Size(min = 2, max = 20, message = "length must be between 2 and 20 characters")
    @Pattern(regexp = "[aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ -]*",
            message = "can contain only Polish letters and space")
    private String customer;

    @NotBlank(message = "must be provided")
    @Pattern(regexp = "\\d{3,4}x\\d{3,4}",
            message = "either width and height must contain 3 or 4 digits and must be separated with 'x'")
    private String dimension;

    @NotNull(message = "must be provided")
    private SendMethod sendMethod;

    @NotBlank(message = "must be provided")
    @Size(min = 2, max = 20, message = "length must be between 2 and 20 characters")
    @Pattern(regexp = "[aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ -]*",
            message = "can contain only Polish letters and space")
    private String payer;

    @Override
    public Proof map() {
        return Proof.builder()
                .id(this.id)
                .printDate(this.printDate)
                .designation(this.designation)
                .quantity(this.quantity)
                .customer(this.customer)
                .dimension(this.dimension)
                .sendMethod(this.sendMethod)
                .payer(this.payer)
                .build();
    }
}
