package pl.wegner.documents.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.wegner.documents.model.dto.ProjectDto;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.repository.specification.FilterCriteria;
import pl.wegner.documents.service.ProjectService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProjectController {

    private ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping("/projects/{id}")
    public Project findProject(@PathVariable long id) {
        return service.find(id);
    }

    @GetMapping("/projects/")
    public Page<Project> findAllProject(@RequestParam int page,
                                        @RequestParam(defaultValue = "20") int size,
                                        @RequestParam(defaultValue = "ASC") Sort.Direction direction,
                                        @RequestBody List<FilterCriteria> criteria) {
        return service.findAll(page, size, direction, criteria);
    }

    @PostMapping("/projects/")
    public Project save(@Valid @RequestBody ProjectDto projectDto) {
        return service.save(projectDto);
    }

    @PutMapping("/projects/")
    public Project edit(@Valid @RequestBody ProjectDto projectDto) {
        return service.edit(projectDto);
    }

    @DeleteMapping("/projects/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
