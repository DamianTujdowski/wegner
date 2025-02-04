package pl.wegner.documents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.entities.OrderArchivalData;
import pl.wegner.documents.model.entities.OrderData;
import pl.wegner.documents.model.enums.LinesPerInch;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderArchivalDataDto {

    @Positive(message = "can't be lower than 1")
    private Long id;

    @PastOrPresent(message = "mustn't be in the future")
    private LocalDate occurrence;

    @NotBlank(message = "must be provided")
    @Size(min = 10, max = 50, message = "length must be between 10 and 50 characters")
    @Pattern(regexp = "[ąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\w ]*",
            message = "can contain only letters (including Polish) and numbers")
    private String fileName;

    @NotBlank(message = "must be provided")
    @Size(min = 19, max = 23, message = "length must be between 19 and 23 characters")
    @Pattern(regexp = "[Zz]lecenie \\d{2} \\d{2} \\d{4}( [Vv]2)?",
            message = "must be formatted like -Zlecenie dd mm yyyy- with optional version mark")
    private String productionFileName;

    @NotBlank(message = "must be provided")
    @Pattern(regexp = "\\d{3,4}x\\d{3,4}",
            message = "either width and height must contain 3 or 4 digits and must be separated with 'x'")
    private String platesDimensions;

    @Positive(message = "can't be lower than 1")
    private int platesQuantity;

    @NotNull
    private LinesPerInch lpi;

    @NotNull
    private PrintSide side;

    @NotEmpty(message = "must be provided and must contain at least one element")
    private List<@Valid InkDto> inks;

    @NotNull
    @Size(max = 500, message = "500 characters allowed")
    @Pattern(regexp = "[ąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\-.,%!': \\w\\s]*",
            message = "can contain only letters (including Polish), numbers, whitespace characters and -.,%!': signs")
    private String notes;

    @NotBlank(message = "must be provided")
    @Size(min = 3, max = 20,
            message = "name length must be between 3 and 20 characters")
    @Pattern(regexp = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ ]*",
            message = "name can contain only letters")
    private String payer;

    @NotBlank(message = "must be provided")
    @Size(min = 3, max = 20,
            message = "name length must be between 3 and 20 characters")
    @Pattern(regexp = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ ]*",
            message = "name can contain only letters")
    private String printHouse;

    @NotBlank(message = "must be provided")
    @Size(min = 3, max = 20,
            message = "name length must be between 3 and 20 characters")
    @Pattern(regexp = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ ]*",
            message = "name can contain only letters")
    private String platesFactory;

    @NotNull(message = "must be provided")
    private PlateThickness plateThickness;

    public OrderArchivalData map() {
        return OrderArchivalData.builder()
                .occurrence(this.occurrence)
                .fileName(this.fileName)
                .productionFileName(this.productionFileName)
                .platesDimensions(this.platesDimensions)
                .platesQuantity(this.platesQuantity)
                .lpi(this.lpi)
                .side(this.side)
                .inks(mapToInks())
                .notes(this.notes)
                .payer(this.payer)
                .printHouse(this.printHouse)
                .platesFactory(this.platesFactory)
                .plateThickness(this.plateThickness)
                .build();
    }

    public OrderData mapToOrderData() {
        return OrderData.builder()
                .fileName(this.fileName)
                .platesDimensions(this.platesDimensions)
                .platesQuantity(this.platesQuantity)
                .lpi(this.lpi)
                .inks(mapToInks())
                .plateThickness(this.plateThickness)
                .side(this.side)
                .build();
    }

    private List<Ink> mapToInks() {
        return this.inks
                .stream()
                .map(InkDto::map)
                .collect(Collectors.toList());
    }

}
