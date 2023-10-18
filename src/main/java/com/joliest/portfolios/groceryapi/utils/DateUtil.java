package com.joliest.portfolios.groceryapi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateUtil {
    public static String DEFAULT_DATE_FORMAT = "MM-dd-yyyy";

    /**
     * Converts a Date to a String in default format (see DEFAULT_DATE_FORMAT)
     * @param date
     * @return a String date formatted as DEFAULT_DATE_FORMAT
     */
    public static String convertDateToDefaultFormat(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return date.format(formatter);
    }

    /**
     * Converts a string to a new LocalDateTime (can parse 2016-10-01 and 2016-10-01 10:15)
     * @param strDate (e.g. "04-21-2023") formatted as DEFAULT_DATE_FORMAT
     * @return a new LocalDateTime
     */
    public static LocalDateTime convertStrToLocalDateTime(String strDate) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern(DEFAULT_DATE_FORMAT)
                .optionalStart()
                .appendPattern(" HH:mm")
                .optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .toFormatter();
        return LocalDateTime.parse(strDate, dateTimeFormatter);
    }
}
