package pl.wegner.documents.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import pl.wegner.documents.model.enums.PlateThickness;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.model.enums.Stage;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String designation;

    private String symbol;

    private String customer;

    private String printHouse;

    private Integer rollerSize;

    private String dimensions;

    @Enumerated(EnumType.ORDINAL)
    private PlateThickness plateThickness;

    @Enumerated(EnumType.ORDINAL)
    private PrintSide side;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "projectId")
    private List<Ink> inks;

    private String notes;

    @Enumerated(EnumType.ORDINAL)
    private Stage stage;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    @JoinColumn(name = "projectId")
    private List<Alteration> alterations;

    private int overallPreparationDuration;

    private LocalDate preparationBeginning;

    private LocalDate preparationEnding;

    public void setInks(List<Ink> inks) {
        if (this.inks == null) {
            this.inks = inks;
        } else {
            this.inks.clear();
            this.inks.addAll(inks);
        }
    }

    public void setAlterations(List<Alteration> alterations) {
        if (this.alterations == null) {
            this.alterations = alterations;
        } else {
            this.alterations.clear();
            this.alterations.addAll(alterations);
        }
    }
}
