package pl.wegner.documents.model.dto;

import lombok.*;
import pl.wegner.documents.model.entities.Alteration;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlterationDto extends Dto implements Mappable<Alteration> {

    @PastOrPresent(message = "mustn't be in the future")
    private LocalDate occurrence;

    @NotBlank(message = "must be provided")
    @Size(min = 10, max = 250, message = "length must be between 10 and 250 characters")
    @Pattern(regexp = "[ąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\w\\s ]*",
            message = "can contain only Polish letters, numbers, space and whitespace characters")
    private String description;

    @PositiveOrZero(message = "can't be lower than 0")
    private int duration;

    @PositiveOrZero(message = "can't be lower than 0")
    private long projectId;

    @Override
    public Alteration map() {
        return Alteration.builder()
                .id(super.getId())
                .occurrence(this.occurrence)
                .description(this.description)
                .duration(this.duration)
                .projectId(this.projectId)
                .build();
    }
}
