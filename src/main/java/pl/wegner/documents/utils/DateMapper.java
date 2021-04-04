package pl.wegner.documents.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateMapper {

    public LocalDate mapSymbolToDate(String symbol) {
        int CENTURY = 2000;
        int year = CENTURY + Integer.valueOf(symbol.substring(0, 2));
        int month = Integer.valueOf(symbol.substring(2, 4));
        int day = Integer.valueOf(symbol.substring(4, 6));
        return LocalDate.of(year, month, day);
    }

    public LocalDate mapJsonDateStringToLocalDate(String date) {
        return LocalDate.parse(date);
    }

}
