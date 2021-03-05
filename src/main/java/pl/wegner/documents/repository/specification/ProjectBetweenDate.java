package pl.wegner.documents.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.wegner.documents.model.entities.Project;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProjectBetweenDate implements Specification<Project> {

    private List<String> dates;

    public ProjectBetweenDate(List<String> dates) {
        this.dates = dates;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.between(root.get("startDate"), dates.get(0), dates.get(1));
    }
}
