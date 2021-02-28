package pl.wegner.documents.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.model.enums.Stage;
import pl.wegner.documents.repository.ProjectRepository;
import pl.wegner.documents.repository.specification.ProjectWithPrintHouse;
import pl.wegner.documents.repository.specification.ProjectWithStage;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project find(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Project with id %d does not exist", id)
                ));
    }

    public Page<Project> findAll(int page, int size, Sort.Direction direction, Stage stage, String printHouse) {
        Specification<Project> spec = buildSpecification(stage, printHouse);
        return repository.findAll(spec, PageRequest.of(page, size, Sort.by(direction, "symbol")));
    }

    private Specification<Project> buildSpecification(Stage stage, String printHouse) {
        return Specification.where(new ProjectWithStage(stage)).or(new ProjectWithPrintHouse(printHouse));
    }

    public Project save(Project project) {
        project.setStartDate(mapSymbolToDate(project.getSymbol()));
        return repository.save(project);
    }

    private LocalDate mapSymbolToDate(String symbol) {
        validateSymbol(symbol);
        int year = mapToPartialDate(symbol, 0, 2);
        int month = mapToPartialDate(symbol, 2, 4);
        int day = mapToPartialDate(symbol, 4, 6);
        return LocalDate.of(year, month, day);
    }

    private Integer mapToPartialDate(String symbol, int beginIndex, int endIndex) {
        return Integer.valueOf(symbol.substring(beginIndex, endIndex));
    }

    //TODO implement symbol validation
    private void validateSymbol(String symbol) {
//        LocalDate.now();
    }

    @Transactional
    public Project edit(Project project) {
        int duration = countOverallPreparationDuration(project.getAlterations());
        Project edited = repository.findById(project.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Project with id %d does not exist", project.getId())
                ));
        edited.setDesignation(project.getDesignation());
        edited.setSymbol(project.getSymbol());
        edited.setClient(project.getClient());
        edited.setPrintHouse(project.getPrintHouse());
        edited.setRollerSize(project.getRollerSize());
        edited.setDimensions(project.getDimensions());
        edited.setPlateThickness(project.getPlateThickness());
        edited.setSide(project.getSide());
        edited.setInks(project.getInks());
        edited.setNotes(project.getNotes());
        edited.setStage(project.getStage());
        edited.setAlterations(project.getAlterations());
        edited.setOverallPreparationDuration(duration);
        return edited;
    }

    private int countOverallPreparationDuration(List<Alteration> alterations) {
        return alterations
                .stream()
                .mapToInt(Alteration::getDuration)
                .sum();
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
