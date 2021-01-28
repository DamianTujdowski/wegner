package pl.wegner.documents.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wegner.documents.model.SendMethod;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Proof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int number;

    private String principal;

    private String dimension;

    @Enumerated(EnumType.ORDINAL)
    private SendMethod method;

    private String payer;
}
