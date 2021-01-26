package pl.wegner.documents.model.entities;

import pl.wegner.documents.model.LinesPerInch;
import pl.wegner.documents.model.PlateThickness;
import pl.wegner.documents.model.PrintSide;

import java.util.List;

public class PlateFactoryOrder {

    private long id;

    private String fileName;

    private String dimensions;

    private int platesNumber;

    private LinesPerInch lpi;

    private List<Ink> inks;

    private PlateThickness plateThickness;

    private PrintSide side;

    private List<Note> notes;
}
