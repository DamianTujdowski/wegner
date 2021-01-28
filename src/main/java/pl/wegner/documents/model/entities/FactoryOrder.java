package pl.wegner.documents.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.LinesPerInch;
import pl.wegner.documents.model.PlateThickness;
import pl.wegner.documents.model.PrintSide;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FactoryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private String dimensions;

    private int platesNumber;

    @Enumerated(EnumType.ORDINAL)
    private LinesPerInch lpi;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "factory_order_id")
    private List<Ink> inks;

    @Enumerated(EnumType.ORDINAL)
    private PlateThickness plateThickness;

    @Enumerated(EnumType.ORDINAL)
    private PrintSide side;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "factory_order_id")
    private List<OrderNote> notes;
}
