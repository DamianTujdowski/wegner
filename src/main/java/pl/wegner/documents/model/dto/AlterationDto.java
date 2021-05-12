package pl.wegner.documents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.entities.Alteration;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlterationDto implements Mappable<Alteration> {

    @PositiveOrZero(message = "can't be lower than 0")
    private long id;

    @PastOrPresent(message = "mustn't be in the future")
    private LocalDate occurrence;

    @NotBlank(message = "must be provided")
    @Size(min = 10, max = 50, message = "length must be between 10 and 50 characters")
    @Pattern(regexp = "[0-9aąbcćdeęfghijklłmnńoópqrsśtuvwxyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUVWXYZŹŻ\\s ]*",
            message = "can contain only Polish letters, numbers, space and new line")
    private String description;

    @PositiveOrZero(message = "can't be lower than 0")
    private int duration;

    @PositiveOrZero(message = "can't be lower than 0")
    private long projectId;

    @Override
    public Alteration map() {
        return Alteration.builder()
                .id(this.id)
                .occurrence(this.occurrence)
                .description(this.description)
                .duration(this.duration)
                .projectId(this.projectId)
                .build();
    }
}
