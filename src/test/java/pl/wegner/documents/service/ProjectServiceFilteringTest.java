package pl.wegner.documents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import pl.wegner.documents.DocumentsApplication;
import pl.wegner.documents.model.entities.Project;
import pl.wegner.documents.repository.ProjectRepository;
import pl.wegner.documents.repository.specification.FilterCriteria;
import pl.wegner.documents.repository.specification.ProjectSpecificationsBuilder;
import pl.wegner.documents.utils.DateMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = DocumentsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:documentsApplication-projectsFilteringTest.properties"
)
@Transactional
class ProjectServiceFilteringTest {

    @Autowired
    private ProjectService service;

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private DateMapper mapper;

    @Autowired
    private ProjectSpecificationsBuilder builder;

    private List<FilterCriteria> printhouseEqualsPalst = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        FilterCriteria printhousePalst = new FilterCriteria();
        printhousePalst.setKey("printHouse");
        printhousePalst.setOperator("equals");
        printhousePalst.setValue("Palst");
        printhouseEqualsPalst.add(printhousePalst);
    }

    @Test
    @Sql(value = "classpath:createProjects.sql")
    public void shouldReturn3Entities_whenSearchingForProjectPrintedByPalst() {
        //given
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, printhouseEqualsPalst);
        //then
        assertEquals(3, result.getTotalElements());
    }

}