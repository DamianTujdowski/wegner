package pl.wegner.documents.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.enums.LinesPerInch;
import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate creationDate;

    private String fileName;

    private String platesDimensions;

    private int platesQuantity;

    @Enumerated(EnumType.ORDINAL)
    private LinesPerInch lpi;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "orderDataId")
    private List<Ink> inks;

    @Enumerated(EnumType.ORDINAL)
    private PlateThickness plateThickness;

    @Enumerated(EnumType.ORDINAL)
    private PrintSide side;

    private String notes;

    private long productionOrderId;

    public void setInks(List<Ink> inks) {
        if (this.inks == null) {
            this.inks = inks;
        } else {
            this.inks.clear();
            this.inks.addAll(inks);
        }
    }

}
