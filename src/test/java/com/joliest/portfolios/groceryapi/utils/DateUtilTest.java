package com.joliest.portfolios.groceryapi.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertDateToDefaultFormat;
import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertStrTimestampToDate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class DateUtilTest {

    @Test
    @DisplayName("When user converts a date to default format, Then it returns a string date")
    void convertDateToDefaultFormatTest() {
        // given
        Date date = convertStrTimestampToDate("2023-05-13T00:00:00.000Z");

        // when
        String actual = convertDateToDefaultFormat(date);

        // then
        String expected = "05-13-2023";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When user converts a String timestamp to Date, Then it returns a Date")
    void convertStrTimestampToDateTest() {
        // given
        String strDate = "2023-05-13T00:00:00.000Z";

        // when
        Date actual = convertStrTimestampToDate(strDate);

        // then
        assertThat(actual).hasSameTimeAs(strDate);
    }
}