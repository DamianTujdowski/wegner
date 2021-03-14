package pl.wegner.documents.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.model.enums.PrintSide;
import pl.wegner.documents.model.enums.Stage;
import pl.wegner.documents.utils.DateMapper;

import java.time.LocalDate;
import java.util.List;

@Component
public class ProjectSpecificationsBuilder {

    private DateMapper dateMapper;

    public ProjectSpecificationsBuilder(DateMapper dateMapper) {
        this.dateMapper = dateMapper;
    }

    public Specification<Project> generateSpecification(List<FilterCriteria> criteria) {
//        Specification<Project> specification = Specification.where(createSpecification(criteria.remove(0)));
//        for (FilterCriteria cr : criteria) {
//            specification = specification.and(createSpecification(cr));
//        }
//        return specification;
        //TODO null check and return sth if list is empty
        return criteria
                .stream()
                .map(this::createSpecification)
                .reduce(Specification::and)
                .orElseThrow(() -> new IllegalArgumentException("Empty list"));
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
            case "le":
                return ((root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get(criteria.getKey()),
                                (LocalDate) castToRequiredType(root.get(criteria.getKey()).getJavaType(),
                                        criteria.getValue())
                        ));
            case "ge":
                return ((root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get(criteria.getKey()),
                                (LocalDate) castToRequiredType(root.get(criteria.getKey()).getJavaType(),
                                        criteria.getValue())
                        ));
            case "between":
                return ((root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.between(
                                root.get(criteria.getKey()),
                                (LocalDate) castToRequiredType(root.get(criteria.getKey()).getJavaType(),
                                        criteria.getValues().get(0)),
                                (LocalDate) castToRequiredType(root.get(criteria.getKey()).getJavaType(),
                                        criteria.getValues().get(1))

                        ));
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
            return value;
        } else if (fieldType.isAssignableFrom(LocalDate.class)) {
            return dateMapper.mapJsonDateStringToLocalDate((String) value);
        } else {
            return null;
        }
    }
}
