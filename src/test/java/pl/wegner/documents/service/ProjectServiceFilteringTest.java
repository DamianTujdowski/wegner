package pl.wegner.documents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.repository.specification.FilterCriteria;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:projectfiltering-integrationtest.properties"
)
class ProjectServiceFilteringTest {

    @Autowired
    private ProjectService service;

    private List<FilterCriteria> printhouseEqualsPalst;

    @BeforeEach
    public void setUp() {
        FilterCriteria printhousePalst = FilterCriteria.builder()
                .key("printHouse")
                .operator("equals")
                .value("Palst")
                .build();
        printhouseEqualsPalst.add(printhousePalst);
    }

    @Test
    public void shouldReturn3Entities_whenSearchingForProjectPrintedByPalst() {
        //given
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, printhouseEqualsPalst);
        //then
        assertEquals(3, result.getTotalElements());
    }

}