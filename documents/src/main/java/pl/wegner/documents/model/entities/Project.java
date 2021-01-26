package pl.wegner.documents.model.entities;

import pl.wegner.documents.model.PlateThickness;
import pl.wegner.documents.model.PrintSide;
import pl.wegner.documents.model.Stage;

import java.util.List;

public class Project {

    private long id;

    private String name;

    private int number;

    private String client;

    private String printHouse;

    private int rollerSize;

    private String dimensions;

    private PlateThickness plateThickness;

    private PrintSide side;

    private List<Ink> inks;

    private List<Note> notes;

    private Stage stage;

    private List<Change> changes;

}
