package pl.wegner.documents.repository.specification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FilterCriteria {

    private String key;

    private String operator;

    private Object value;

    private List<Object> values;

}
