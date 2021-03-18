package pl.wegner.documents.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wegner.documents.model.entities.Project;

import java.time.LocalDate;

@SpringBootTest
class IntegrationProjectServiceTest {

    @Autowired
    private ProjectService service;

    @Test
    public void forProjectWithSymbol210213_shouldGenerateDate2021_02_13() {
        //given
        Project project = Project.builder()
                .client("Palst")
                .rollerSize(300)
                .designation("Alicja dzialoszyn")
                .symbol("210213")
                .build();
        Project saved;
        LocalDate date = LocalDate.of(2021, 2, 13);
        //when
        saved = service.save(project);
        //then
        Assertions.assertEquals(date, saved.getPreparationBeginning());
    }

}