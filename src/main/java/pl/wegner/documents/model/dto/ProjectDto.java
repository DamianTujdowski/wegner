package pl.wegner.documents.model.dto;

import lombok.*;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.model.enums.Stage;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectDto {

    private long id;

    @NotBlank(message = "Project designation must be provided")
    @Size(min = 10, max = 50, message = "Designation length must be between 10 and 50 characters")
    @Pattern(regexp = "[aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ]*",
            message = "Designation can contain only letters and numbers")
    private String designation;

    @NotBlank(message = "Project symbol must be provided")
    @Pattern(regexp = "\\d{8}/\\d{2}",
            message = "Symbol can contain only digits and slash. " +
                    "There must be 8 digits before slash and 2 digits after it")
    private String symbol;

    @NotBlank(message = "Project customer must be provided")
    @Size(min = 3, max = 20,
            message = "Symbol length must be between 3 and 20 characters")
    @Pattern(regexp = "[aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ]*",
            message = "Customer name can contain only letters")
    private String customer;

    @NotBlank(message = "Project print house must be provided")
    @Size(min = 3, max = 20,
            message = "Symbol length must be between 3 and 20 characters")
    @Pattern(regexp = "[aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ]*",
            message = "Print house name can contain only letters")
    private String printHouse;

    @NotNull(message = "Project roller size must be provided")
    @Min(value = 250, message = "Roller size must be greater than or equal to 250")
    @Max(value = 600, message = "Roller size must be less than or equal to 600")
    private Integer rollerSize;

    @NotBlank(message = "Project dimensions must be provided")
    @Pattern(regexp = "\\d{3}x\\d{3}",
            message = "Either width and height must contain 3 digits and must be separated with 'x'")
    private String dimensions;

    @NotNull(message = "Project plate thickness must be provided")
    private PlateThickness plateThickness;

    @NotNull(message = "Project print side must be provided")
    private PrintSide side;

    @NotEmpty(message = "Project inks must be provided")
    private List<Ink> inks;

    private String notes;

    @NotNull(message = "Project stage must be provided")
    private Stage stage;

    @NotNull(message = "Project alterations list can't be null")
    private List<Alteration> alterations;

    @PositiveOrZero(message = "Overall preparation duration can't be lower than 0")
    private int overallPreparationDuration;

    private LocalDate preparationBeginning;

    @PastOrPresent(message = "Preparation ending date mustn't be in the future")
    private LocalDate preparationEnding;

}
