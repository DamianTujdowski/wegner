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
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderArchivalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate occurrence;

    private String fileName;

    private String productionFileName;

    private String platesDimensions;

    private int platesQuantity;

    @Enumerated(EnumType.ORDINAL)
    private LinesPerInch lpi;

    private PrintSide side;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "order_archival_data_id")
    private List<Ink> inks;

    private String notes;

    private String payer;

    private String printHouse;

    private String platesFactory;

    @Enumerated(EnumType.ORDINAL)
    private PlateThickness plateThickness;

    public void setInks(List<Ink> inks) {
        if (this.inks == null) {
            this.inks = inks;
        } else {
            this.inks.clear();
            this.inks.addAll(inks);
        }
    }

}
