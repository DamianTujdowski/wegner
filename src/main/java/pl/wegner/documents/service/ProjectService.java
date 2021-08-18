package pl.wegner.documents.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.wegner.documents.model.dto.ProjectDto;
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

    public Project save(ProjectDto projectDto) {
        Project newProject = projectDto.map();
        newProject.setPreparationBeginning(dateMapper.mapSymbolToDate(newProject.getSymbol()));
        return repository.save(newProject);
    }

    @Transactional
    public Project edit(ProjectDto projectDto) {
        int duration = projectDto.countOverallPreparationDuration();
        Project edited = repository.findById(projectDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Project with id %d does not exist", projectDto.getId())
                ));
        edited.setDesignation(projectDto.getDesignation());
        edited.setSymbol(projectDto.getSymbol());
        edited.setCustomer(projectDto.getCustomer());
        edited.setPrintHouse(projectDto.getPrintHouse());
        edited.setRollerSize(projectDto.getRollerSize());
        edited.setDimensions(projectDto.getDimensions());
        edited.setPlateThickness(projectDto.getPlateThickness());
        edited.setSide(projectDto.getSide());
//this line is throwing error - this method is going to be refactored anyway
        //        edited.setInks(projectDto.getInks());
        edited.setNotes(projectDto.getNotes());
        edited.setStage(projectDto.getStage());
        //this line is throwing error - this method is going to be refactored anyway
//        edited.setAlterations(projectDto.getAlterations());
        edited.setOverallPreparationDuration(duration);
        edited.setPreparationBeginning(projectDto.getPreparationBeginning());
        edited.setPreparationEnding(projectDto.getPreparationEnding());
        return edited;
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
