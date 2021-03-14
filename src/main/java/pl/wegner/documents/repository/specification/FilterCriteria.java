package pl.wegner.documents.repository.specification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FilterCriteria {

    private String key;

    private String operator;

    private Object value;

    private List<LocalDate> values;

}
