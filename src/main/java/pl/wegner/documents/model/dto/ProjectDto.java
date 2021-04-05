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

    @NotBlank(message = "must be provided")
    @Size(min = 10, max = 50, message = "length must be between 10 and 50 characters")
    @Pattern(regexp = "[0-9aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ]*",
            message = "can contain only Polish letters and numbers")
    private String designation;

    @NotBlank(message = "must be provided")
    @Pattern(regexp = "\\d{8}/\\d{2}",
            message = "can contain only digits and slash. " +
                    "There must be 8 digits before slash and 2 digits after it")
    private String symbol;

    @NotBlank(message = "must be provided")
    @Size(min = 3, max = 20,
            message = "name length must be between 3 and 20 characters")
    @Pattern(regexp = "[aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ]*",
            message = "name can contain only letters")
    private String customer;

    @NotBlank(message = "must be provided")
    @Size(min = 3, max = 20,
            message = "name length must be between 3 and 20 characters")
    @Pattern(regexp = "[aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ]*",
            message = "name can contain only letters")
    private String printHouse;

    @NotNull(message = "must be provided")
    @Min(value = 250, message = "must be greater than or equal to 250")
    @Max(value = 600, message = "must be less than or equal to 600")
    private Integer rollerSize;

    @NotBlank(message = "must be provided")
    @Pattern(regexp = "\\d{3,4}x\\d{3,4}",
            message = "Either width and height must contain 3 or 4 digits and must be separated with 'x'")
    private String dimensions;

    @NotNull(message = "must be provided")
    private PlateThickness plateThickness;

    @NotNull(message = "must be provided")
    private PrintSide side;

    @NotEmpty(message = "must be provided")
    private List<Ink> inks;

    private String notes;

    @NotNull(message = "must be provided")
    private Stage stage;

    @NotNull(message = "can't be null")
    private List<Alteration> alterations;

    @PositiveOrZero(message = "can't be lower than 0")
    private int overallPreparationDuration;

    private LocalDate preparationBeginning;

    @PastOrPresent(message = "mustn't be in the future")
    private LocalDate preparationEnding;

}
