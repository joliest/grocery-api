package com.joliest.portfolios.groceryapi.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertDateToDefaultFormat;
import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertStrToLocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class DateUtilTest {

    @Test
    @DisplayName("When user converts a date to default format, Then it returns a string date")
    void convertDateToDefaultFormatTest() {
        // given
        LocalDate localDate = LocalDate.parse("2023-05-13");
        LocalTime localTime = LocalTime.parse("07:52:41.197653");
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        // when
        String actual = convertDateToDefaultFormat(localDateTime);

        // then
        String expected = "05-13-2023";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When user converts a String date to LocalDateTime, Then it returns a LocalDateTime")
    void convertStrTimestampToDateTest() {
        // given
        String strDate = "04-21-2023";

        // when
        LocalDateTime actual = convertStrToLocalDateTime(strDate);

        // then
        assertThat(actual).hasMonth(Month.APRIL);
        assertThat(actual).hasYear(2023);
        assertThat(actual).hasDayOfMonth(21);
    }
    @Test
    @DisplayName("When time is provided in the string, Then it returns a LocalDate")
    void convertStrTimestampToDateTest_hasTime() {
        // given
        String strDate = "04-21-2023 10:15";

        // when
        LocalDateTime actual = convertStrToLocalDateTime(strDate);

        // then
        assertThat(actual).hasMonth(Month.APRIL);
        assertThat(actual).hasYear(2023);
        assertThat(actual).hasDayOfMonth(21);

        assertThat(actual).hasHour(10);
        assertThat(actual).hasMinute(15);
    }
}