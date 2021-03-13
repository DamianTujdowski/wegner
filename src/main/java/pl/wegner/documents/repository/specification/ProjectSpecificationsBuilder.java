package pl.wegner.documents.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.model.enums.Stage;

import java.util.List;

@Component
public class ProjectSpecificationsBuilder {

    public Specification<Project> generateSpecification(List<FilterCriteria> criteria) {
        Specification<Project> specification = Specification.where(createSpecification(criteria.remove(0)));
        for (FilterCriteria cr : criteria) {
            specification = specification.and(createSpecification(cr));
        }
        return specification;
    }

    private Specification<Project> createSpecification(FilterCriteria criteria) {

        switch (criteria.getOperator()) {
            case "equals":
                return ((root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(
                                root.get(criteria.getKey()),
                                castToRequiredType(root.get(criteria.getKey()).getJavaType(),
                                        criteria.getValue())
                        ));
//            case "in":
//                return ((root, criteriaQuery, criteriaBuilder) ->
//                        criteriaBuilder.in(root.<String>get(criteria.getKey()))
//                                .value(root.get(criteria.getKey()).getJavaType(), criteria.getValues()));
            case "less_than":
                return ((root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.lt(
                                root.get(criteria.getKey()),
                                (Number) castToRequiredType(root.get(criteria.getKey()).getJavaType(),
                                        criteria.getValue())
                        ));

            case "greater_than":
                return (((root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(criteria.getKey()), (Number) criteria.getValue())));

            default:
                throw new RuntimeException("Operation not supported");
        }
    }

    private Object castToRequiredType(Class fieldType, Object value) {
        if (fieldType.isAssignableFrom(Stage.class)) {
            return Enum.valueOf(fieldType, value.toString());
        } else if (fieldType.isAssignableFrom(PrintSide.class)) {
            return Enum.valueOf(fieldType, value.toString());
        } else if (fieldType.isAssignableFrom(String.class)) {
            return value.toString();
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return value;
        }

        return null;
    }
}
