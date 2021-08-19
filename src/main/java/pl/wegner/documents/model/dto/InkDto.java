package pl.wegner.documents.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.LinesPerInch;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InkDto {

    @Positive(message = "can't be lower than 1")
    private Long id;

    @NotBlank(message = "must be provided")
    @Size(min = 4, max = 25, message = "length must be between 4 and 50 characters")
    @Pattern(regexp = "[\\w ]*",
            message = "can contain only letters, numbers and space")
    private String symbol;

    private Angle angle;

    private LinesPerInch lpi;

    @Positive(message = "can't be lower than 1")
    private Long projectId;

    @Positive(message = "can't be lower than 1")
    private Long orderDataId;

    @Positive(message = "can't be lower than 1")
    private Long orderArchivalDataId;

    public Ink map() {
        return Ink.builder()
                .symbol(this.symbol)
                .angle(this.angle)
                .lpi(this.lpi)
                .projectId(this.projectId)
                .orderDataId(this.orderDataId)
                .orderArchivalDataId(this.orderArchivalDataId)
                .build();
    }

}
