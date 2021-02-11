package pl.wegner.documents.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import pl.wegner.documents.model.enums.Angle;
import pl.wegner.documents.model.enums.LinesPerInch;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String symbol;

    @Enumerated(EnumType.ORDINAL)
    private Angle angle;

    @Enumerated(EnumType.ORDINAL)
    private LinesPerInch lpi;

    @Column(name = "project_id")
    private long projectId;

    @Column(name = "order_data_id")
    private long orderDataId;

    @Column(name = "order_archival_data_id")
    private long orderArchivalDataId;

}
