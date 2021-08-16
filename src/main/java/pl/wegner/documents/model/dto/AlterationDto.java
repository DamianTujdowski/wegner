package pl.wegner.documents.model.dto;

import lombok.*;
import pl.wegner.documents.model.entities.Alteration;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlterationDto implements Mappable<Alteration> {

    @Positive(message = "can't be lower than 1")
    private Long id;

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
                .occurrence(this.occurrence)
                .description(this.description)
                .duration(this.duration)
                .projectId(this.projectId)
                .build();
    }
}
