package pl.wegner.documents.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.wegner.documents.model.entities.Project;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProjectWithPrintHouse implements Specification<Project> {

    private String printHouse;

    public ProjectWithPrintHouse(String printHouse) {
        this.printHouse = printHouse;
    }


    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (printHouse == null) {
            return builder.isTrue(builder.literal(false));
        }
        return builder.equal(root.get("printHouse"), this.printHouse);
    }
}
