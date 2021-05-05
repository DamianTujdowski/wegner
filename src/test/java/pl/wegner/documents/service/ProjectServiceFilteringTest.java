package pl.wegner.documents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = DocumentsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:documentsApplication-projectsFilteringTest.properties"
)
@Transactional
@Sql(value = "classpath:createProjects.sql")
class ProjectServiceFilteringTest {

    @Autowired
    private ProjectService service;

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private DateMapper mapper;

    @Autowired
    private ProjectSpecificationsBuilder builder;

    private FilterCriteria printHousePalst, printHouseFlorek, stageInAcceptation, stageProof, customerAlicja,
            leStartDate2021_01_15, leStartDate2021_03_20, geStartDate2021_03_01, between2021_01_15And2021_03_01,
            leEndDate2021_04_05;

    @BeforeEach
    void setUp() {
        printHousePalst = new FilterCriteria();
        printHousePalst.setKey("printHouse");
        printHousePalst.setOperator("equals");
        printHousePalst.setValue("Palst");

        printHouseFlorek = new FilterCriteria();
        printHouseFlorek.setKey("printHouse");
        printHouseFlorek.setOperator("equals");
        printHouseFlorek.setValue("Florek");

        stageInAcceptation = new FilterCriteria();
        stageInAcceptation.setKey("stage");
        stageInAcceptation.setOperator("equals");
        stageInAcceptation.setValue("IN_ACCEPTATION");

        stageProof = new FilterCriteria();
        stageProof.setKey("stage");
        stageProof.setOperator("equals");
        stageProof.setValue("PROOF");

        customerAlicja = new FilterCriteria();
        customerAlicja.setKey("customer");
        customerAlicja.setOperator("equals");
        customerAlicja.setValue("Alicja");

        leStartDate2021_01_15 = new FilterCriteria();
        leStartDate2021_01_15.setKey("preparationBeginning");
        leStartDate2021_01_15.setOperator("le");
        leStartDate2021_01_15.setValue("2021-01-15");

        leStartDate2021_03_20 = new FilterCriteria();
        leStartDate2021_03_20.setKey("preparationBeginning");
        leStartDate2021_03_20.setOperator("le");
        leStartDate2021_03_20.setValue("2021-03-20");

        leEndDate2021_04_05 = new FilterCriteria();
        leEndDate2021_04_05.setKey("preparationEnding");
        leEndDate2021_04_05.setOperator("le");
        leEndDate2021_04_05.setValue("2021-04-05");

        geStartDate2021_03_01 = new FilterCriteria();
        geStartDate2021_03_01.setKey("preparationBeginning");
        geStartDate2021_03_01.setOperator("ge");
        geStartDate2021_03_01.setValue("2021-03-01");

        List<Object> dates = Stream.of("2021-01-15", "2021-03-01").collect(Collectors.toList());
        between2021_01_15And2021_03_01 = new FilterCriteria();
        between2021_01_15And2021_03_01.setKey("preparationBeginning");
        between2021_01_15And2021_03_01.setOperator("between");
        between2021_01_15And2021_03_01.setValues(dates);
    }

    @Test
    void shouldReturnAllProjects_whenFilterCriteriaListIsEmpty() {
        //given
        List<FilterCriteria> criteria = new ArrayList<>();
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(5, result.getTotalElements());
    }

    @Test
    void shouldReturnThreeProjects_whenSearchingForProjectPrintedByPalst() {
        //given
        List<FilterCriteria> criteria = Stream.of(printHousePalst).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void shouldReturnOneProject_whenSearchingForProjectsPrintedByFlorek() {
        //given
        List<FilterCriteria> criteria = Stream.of(printHouseFlorek).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void shouldReturnOneProject_whenSearchingForProjectsInStageIn_Acceptation() {
        //given
        List<FilterCriteria> criteria = Stream.of(stageInAcceptation).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void shouldReturnThreeProjects_whenSearchingForProjectsInStageProof() {
        //given
        List<FilterCriteria> criteria = Stream.of(stageProof).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void shouldReturnTwoProjects_whenSearchingForProjectsOrderedByCustomerAlicja() {
        //given
        List<FilterCriteria> criteria = Stream.of(customerAlicja).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void shouldReturnOneProject_whenSearchingForProjectsOrderedByCustomerAlicjaAndPrintedByPalst() {
        //given
        List<FilterCriteria> criteria = Stream.of(customerAlicja, printHousePalst).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void shouldReturnTwoProjects_whenSearchingForProjectsStartedAt2021_01_15OrEarlier() {
        //given
        List<FilterCriteria> criteria = Stream.of(leStartDate2021_01_15).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void shouldReturnTwoProjects_whenSearchingForProjectsStartedAt2021_03_01OrLater() {
        //given
        List<FilterCriteria> criteria = Stream.of(geStartDate2021_03_01).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void shouldReturnThreeProjects_whenSearchingForProjectsStartedBetween2021_01_15And2021_03_01() {
        //given
        List<FilterCriteria> criteria = Stream.of(between2021_01_15And2021_03_01).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void shouldReturnZeroProjects_whenSearchingForProjectsStartedBefore2021_01_15AndAfter2021_03_01() {
        //given
        List<FilterCriteria> criteria = Stream.of(leStartDate2021_01_15, geStartDate2021_03_01).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void shouldReturnTwoProjects_whenSearchingForProjectsStartedBefore2021_3_20AndAfter2021_03_01() {
        //given
        List<FilterCriteria> criteria = Stream.of(leStartDate2021_03_20, geStartDate2021_03_01).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void shouldReturnTwoProjects_whenSearchingForProjectsStarted2021_03_01AndEnded2021_04_05() {
        //given
        List<FilterCriteria> criteria = Stream.of(geStartDate2021_03_01, leEndDate2021_04_05).collect(Collectors.toList());
        Page<Project> result;
        //when
        result = service.findAll(0, 20, Sort.Direction.ASC, criteria);
        //then
        assertEquals(2, result.getTotalElements());
    }

}