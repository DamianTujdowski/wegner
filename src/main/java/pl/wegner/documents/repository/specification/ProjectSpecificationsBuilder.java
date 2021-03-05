package pl.wegner.documents.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.wegner.documents.model.entities.Project;

import java.util.List;

public class ProjectSpecificationsBuilder {

    private final List<FilterCriteria> criteria;

    public ProjectSpecificationsBuilder(List<FilterCriteria> criteria) {
        this.criteria = criteria;
    }

    public Specification<Project> generateSpecification() {
        return getSpecificationFromFilter(this.criteria);
    }

    private Specification<Project> getSpecificationFromFilter(List<FilterCriteria> criteria) {
        Specification<Project> specification = Specification.where(createSpecification(criteria.remove(0)));
        criteria.forEach(c -> specification.and(createSpecification(c)));
        return specification;
    }

    private Specification<Project> createSpecification(FilterCriteria criteria) {

        switch (criteria.getOperator()) {
            case "equals":
                return ((root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(
                                root.<String>get(criteria.getKey()), criteria.getValue().toString()));
//            case "in":
//                return ((root, criteriaQuery, criteriaBuilder) ->
//                        criteriaBuilder.in(root.<String>get(criteria.getKey()))
//                                .value(root.get(criteria.getKey()).getJavaType(), criteria.getValues()));
//            case "lt":
//                return (((root, criteriaQuery, criteriaBuilder) ->
//                        criteriaBuilder.lt(root)))

            default:
                throw new RuntimeException("Operation not supported");
        }
    }
}
