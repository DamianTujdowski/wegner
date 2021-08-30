package pl.wegner.documents.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.wegner.documents.model.dto.ProjectDto;
import pl.wegner.documents.model.entities.Ink;
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

    private ProjectRepository projectRepository;

    private ProjectSpecificationsBuilder builder;

    private DateMapper dateMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectSpecificationsBuilder builder, DateMapper dateMapper) {
        this.projectRepository = projectRepository;
        this.builder = builder;
        this.dateMapper = dateMapper;
    }

    public Project find(long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Project with id %d does not exist", id)
                ));
    }

    public Page<Project> findAll(int page, int size, Sort.Direction direction, List<FilterCriteria> criteria) {
        Specification<Project> spec = builder.generateSpecification(criteria);
        return projectRepository.findAll(spec, PageRequest.of(page, size, Sort.by(direction, "symbol")));
    }

    public Project save(ProjectDto projectDto) {
        Project newProject = projectDto.map();
        newProject.setPreparationBeginning(dateMapper.mapSymbolToDate(newProject.getSymbol()));
        return projectRepository.save(newProject);
    }

    @Transactional
    public Project edit(ProjectDto projectDto) {
        int duration = projectDto.countOverallPreparationDuration();
        Project edited = projectRepository.findById(projectDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Project with id %d does not exist", projectDto.getId())
                ));
//        List<Ink> inksEdited = projectRepository.findAllInks();
        List<Ink> inksEdited = edited.getInks();
        inksEdited.forEach(ink -> ink.setId(555L));
        edited.setDesignation(projectDto.getDesignation());
        edited.setSymbol(projectDto.getSymbol());
        edited.setCustomer(projectDto.getCustomer());
        edited.setPrintHouse(projectDto.getPrintHouse());
        edited.setRollerSize(projectDto.getRollerSize());
        edited.setDimensions(projectDto.getDimensions());
        edited.setPlateThickness(projectDto.getPlateThickness());
        edited.setSide(projectDto.getSide());
        //
        edited.setInks(inksEdited);
//        edited.setInks(projectDto.mapToInks());
        edited.setNotes(projectDto.getNotes());
        edited.setStage(projectDto.getStage());
        //
//        edited.setAlterations(projectDto.mapToAlteration());
        edited.setOverallPreparationDuration(duration);
        edited.setPreparationBeginning(projectDto.getPreparationBeginning());
        edited.setPreparationEnding(projectDto.getPreparationEnding());
        return edited;
    }

    public void delete(long id) {
        projectRepository.deleteById(id);
    }
}
