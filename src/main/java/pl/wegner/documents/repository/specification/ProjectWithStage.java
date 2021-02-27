package pl.wegner.documents.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.model.enums.Stage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProjectWithStage implements Specification<Project> {

    Stage stage;

    public ProjectWithStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
        if (stage == null) {
            return builder.isTrue(builder.literal(true));
        }
        return builder.equal(root.get("stage"), this.stage);
    }
}
