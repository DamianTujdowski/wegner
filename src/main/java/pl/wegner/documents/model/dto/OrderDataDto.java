package pl.wegner.documents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDataDto {

    private long id;

    @PastOrPresent(message = "mustn't be in the future")
    private LocalDate creationDate;

    @NotBlank(message = "must be provided")
    @Size(min = 10, max = 30, message = "length must be between 10 and 30 characters")
    @Pattern(regexp = "[\\w ]*",
            message = "can contain only letters, digits, underscore and space")
    private String fileName;

    @NotBlank(message = "must be provided")
    @Pattern(regexp = "\\d{3,4}x\\d{3,4}",
            message = "Either width and height must contain 3 or 4 digits and must be separated with 'x'")
    private String platesDimensions;

    @Min(value = 1, message = "must be at least one")
    @Max(value = 8, message = "maximally can be 8")
    private int platesQuantity;

    @NotEmpty(message = "must be provided and must contain at least one element")
    private List<Ink> inks;

    @NotNull(message = "must be provided")
    private PlateThickness plateThickness;

    @NotNull(message = "must be provided")
    private PrintSide side;

    private String notes;

    private long productionOrderId;
}
