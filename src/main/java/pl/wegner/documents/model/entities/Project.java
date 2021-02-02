package pl.wegner.documents.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import pl.wegner.documents.model.PlateThickness;
import pl.wegner.documents.model.PrintSide;
import pl.wegner.documents.model.Stage;

import javax.persistence.*;
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

    private String name;

    private int number;

    private String client;

    private String printHouse;

    private int rollerSize;

    private String dimensions;

    @Enumerated(EnumType.ORDINAL)
    private PlateThickness plateThickness;

    @Enumerated(EnumType.ORDINAL)
    private PrintSide side;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<Ink> inks;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<Note> notes;

    @Enumerated(EnumType.ORDINAL)
    private Stage stage;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<Alteration> alterations;

}
