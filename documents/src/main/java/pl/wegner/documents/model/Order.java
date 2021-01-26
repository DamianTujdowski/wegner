package pl.wegner.documents.model;

import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.entities.Note;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private long id;

    private LocalDateTime date;

    private String fileName;

    private String productionFileName;

    private String dimensions;

    private int platesNumber;

    private LinesPerInch lpi;

    private PrintSide side;

    private List<Ink> inks;

    private List<Note> notes;

    private String payer;

    private String printHouse;

    private String platesFactory;

    private PlateThickness plateThickness;
}
