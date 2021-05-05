package pl.wegner.documents.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class DateMapperTest {

    @TestConfiguration
    static class DateMapperTestConfiguration {

        @Bean
        DateMapper dateMapper() {
            return new DateMapper();
        }
    }

    @Autowired
    private DateMapper mapper;

    @Test
    void forSymbol_21021301_shouldGenerateDate2021_02_13() {
        //given
        String symbol = "21021301";
        LocalDate date = LocalDate.of(2021, 2, 13);
        //when
        LocalDate result = mapper.mapSymbolToDate(symbol);
        //then
        assertEquals(date,result);
    }

    @Test
    void forSymbol_20103001_shouldGenerateDate2021_02_13() {
        //given
        String symbol = "20103001";
        LocalDate date = LocalDate.of(2020, 10, 30);
        //when
        LocalDate result = mapper.mapSymbolToDate(symbol);
        //then
        assertEquals(date,result);
    }

}