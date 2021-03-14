package pl.wegner.documents.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.wegner.documents.model.entities.Alteration;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.repository.ProjectRepository;
import pl.wegner.documents.repository.specification.FilterCriteria;
import pl.wegner.documents.repository.specification.ProjectSpecificationsBuilder;
import pl.wegner.documents.utils.DateMapper;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository repository;

    private ProjectSpecificationsBuilder builder;

    private DateMapper dateMapper;

    public ProjectService(ProjectRepository repository, ProjectSpecificationsBuilder builder, DateMapper dateMapper) {
        this.repository = repository;
        this.builder = builder;
        this.dateMapper = dateMapper;
    }

    public Project find(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Project with id %d does not exist", id)
                ));
    }

    public Page<Project> findAll(int page, int size, Sort.Direction direction, List<FilterCriteria> criteria) {
        Specification<Project> spec = builder.generateSpecification(criteria);
        return repository.findAll(spec, PageRequest.of(page, size, Sort.by(direction, "symbol")));
    }

    public Project save(Project project) {
        project.setPreparationBeginning(dateMapper.mapSymbolToDate(project.getSymbol()));
        return repository.save(project);
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
        edited.setPreparationBeginning(project.getPreparationBeginning());
        edited.setPreparationEnding(project.getPreparationEnding());
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
