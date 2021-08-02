package pl.wegner.documents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.entities.Attributes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributesDto implements Mappable<Attributes> {

    //TODO check how to trigger validation, in Production Order Dto,
    //TODO write some tests

    @NotBlank(message = "must be provided")
    @Size(min = 2, max = 30, message = "between 2 and 30 characters allowed")
    @Pattern(regexp = "[aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ ]*",
            message = "can contain only letters (including Polish) and space")
    private String operatorName;

    @NotBlank(message = "must be provided")
    @Size(min = 9, max = 15, message = "length must be between 9 and 15 characters")
    @Pattern(regexp = "(\\+48 )?\\d{3}[ -]?\\d{3}[ -]?\\d{3}",
            message = "preferable format NNN-NNN-NNN, dashes can be replaced with spaces, \"+48 \" prefix allowed")
    private String telephoneNumber;

    @NotNull(message = "can't be null")
    @Size(max = 500, message = "500 characters allowed")
    @Pattern(regexp = "[ąćęłńóśźżĄĆĘŁŃÓŚŹŻ \\w]*",
            message = "can contain only letters (including Polish), numbers and space")
    private String occasionalComments;

    @NotBlank(message = "must be provided")
    @Size(min = 5, max = 500, message = "between 5 and 500 characters allowed")
    @Pattern(regexp = "[ąćęłńóśźżĄĆĘŁŃÓŚŹŻ \\w]*",
            message = "can contain only letters (including Polish), numbers and space")
    private String technicalComments;

    @Override
    public Attributes map() {
        return Attributes.builder()
                .operatorName(this.operatorName)
                .telephoneNumber(this.telephoneNumber)
                .occasionalComments(this.occasionalComments)
                .technicalComments(this.technicalComments)
                .build();
    }
}
